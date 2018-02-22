package ru.server.spring.schedulers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.server.spring.configs.AdminConfiguration;
import ru.server.spring.configs.SchedulersConfiguration;
import ru.server.spring.models.api.NotificationApi;
import ru.server.spring.services.AdminService;
import ru.server.spring.services.NotificationService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
@EnableScheduling
public class TaskLogScheduler {

    private final AdminService adminService;
    private final SchedulersConfiguration schedulersConfiguration;
    private final NotificationService notificationService;
    private final String domain;

    private Instant lastReactionTime;

    public TaskLogScheduler(AdminService adminService, SchedulersConfiguration schedulersConfiguration, NotificationService notificationService, AdminConfiguration adminConfiguration) {
        this.adminService = adminService;
        this.schedulersConfiguration = schedulersConfiguration;
        this.notificationService = notificationService;
        this.domain = adminConfiguration.getAdminProperties().getDomain();

        getFirstLastReactionTime();
    }

    @Scheduled(cron = "${schedulers.task-log.cron}")
    private void checkStatusChangeTaskLog() {
        if (schedulersConfiguration.getSchedulersProperties().getTaskLog().getEnable()) {
            String today = today();

            JsonObject response = adminService.getProblemTickets(100, today, today);
            JsonArray tickets = response.getAsJsonArray("tickets");

            for (JsonElement ticket : tickets) {
                Instant reactionTxtime = parseReactionTime(ticket.getAsJsonObject().get("reactionTxtime").getAsString());

                if (reactionTxtime.getEpochSecond() <= lastReactionTime.getEpochSecond()) break;

                int id = ticket.getAsJsonObject().get("id").getAsInt();
                String subject = ticket.getAsJsonObject().get("subject").getAsString();
                JsonObject lastComment = ticket.getAsJsonObject().get("lastComment").getAsJsonObject();

                int statusFrom = lastComment.get("statusFrom").getAsInt();
                int statusTo = lastComment.get("statusTo").getAsInt();

                String message = null;

                if (statusFrom == 2 && statusTo == 4) // Открыто -> На удержании
                    message = notificationMessage(id, subject, "Открыто -> На удержании");

                if (statusFrom == 4 && statusTo == 3) // На удержании -> На ожидании
                    message = notificationMessage(id, subject, "На удержании -> На ожидании");

                if (statusFrom == 4 && statusTo == 5) // На удержании -> Решено
                    message = notificationMessage(id, subject, "На удержании -> Решено");

                if (statusFrom == 3 && statusTo == 5) // На ожидании -> Решено
                    message = notificationMessage(id, subject, "На ожидании -> Решено");

                if (message != null) sendNotifications(id, message);
            }

            setLastReactionTime(response);
        }
    }


    private void getFirstLastReactionTime() {
        if (schedulersConfiguration.getSchedulersProperties().getTaskLog().getEnable()) {
            String today = today();
            JsonObject response = adminService.getProblemTickets(1, today, today);
            setLastReactionTime(response);
        }
    }

    private void setLastReactionTime(JsonObject jsonObject) {
        JsonArray tickets = jsonObject.getAsJsonArray("tickets");

        if (tickets.size() == 0 && this.lastReactionTime == null)
            this.lastReactionTime = Instant.now();

        if (tickets.size() > 0)
            this.lastReactionTime = parseReactionTime(tickets.get(0).getAsJsonObject().get("reactionTxtime").getAsString());
    }

    private Instant parseReactionTime(String s) {
        LocalDateTime localDateTime = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    private String today() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.UK).withZone(ZoneId.systemDefault());
        Instant instant = Instant.now();

        return formatter.format(instant);
    }

    private void sendNotifications(int id, String body) {
        JsonArray incidentTicketIds = adminService.getTicket(id).get("incidentTicketIds").getAsJsonArray();

        Set<String> toUsers = new HashSet<>();
        for (JsonElement incidentTicketId : incidentTicketIds) {
            JsonObject incidentTicket = adminService.getTicket(incidentTicketId.getAsInt());

            int assigneeId = incidentTicket.get("assigneeId").getAsInt();

            toUsers.add(Integer.toString(assigneeId));
        }

        NotificationApi notification = new NotificationApi();

        notification.setHead("Task log");
        notification.setBody(body);
        notification.setLink(domain + "/helpdesk/details/" + id);
        notification.setFrom((long) 1);
        notification.setType("adm-user-id");
        notification.setTo(toUsers);

        notificationService.add(notification);
    }

    private String notificationMessage(int id, String subject, String status) {
        return "Проблема #" + id + "\n" +
                "Тема: " + subject + "\n\n" +
                "Изменился статус \"" + status + "\"";
    }

}
