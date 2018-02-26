package ru.server.spring.schedulers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.server.spring.configs.SchedulersConfiguration;
import ru.server.spring.models.User;
import ru.server.spring.services.AdminService;
import ru.server.spring.services.UserService;

import java.util.List;

@Component
@EnableScheduling
public class AdminScheduler {

    private final SchedulersConfiguration schedulersConfiguration;
    private final AdminService adminService;
    private final UserService userService;

    private String moderGroupHelpDeskCount;
    private String domofondGroupHelpDeskCount;

    public AdminScheduler(SchedulersConfiguration schedulersConfiguration, AdminService adminService, UserService userService) {
        this.schedulersConfiguration = schedulersConfiguration;
        this.adminService = adminService;
        this.userService = userService;
    }

    @Scheduled(cron = "${schedulers.admin-user-id.cron}")
    private void updateAdminUserId() {
        if (schedulersConfiguration.getSchedulersProperties().getAdminUserId().getEnable()) {
            JsonArray admUsers = adminService.getUserAdminList();
            List<User> users = userService.getUsersWithNullAdmUserId();

            for (User user : users) {
                String userEmail = user.getEmail();

                for (JsonElement admUser : admUsers) {
                    String admUserEmail = admUser.getAsJsonObject().get("email").getAsString();

                    if (userEmail.equals(admUserEmail)) {
                        user.setAdm_user_id(admUser.getAsJsonObject().get("id").getAsLong());

                        userService.update(user);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "${schedulers.hd-groups-stat.cron}")
    private void updateGroupHelpDeskCount() {
        if (schedulersConfiguration.getSchedulersProperties().getHdGroupsStat().getEnable()) {
            Gson gson = new Gson();

            this.moderGroupHelpDeskCount = gson.toJson(adminService.getGroupFilterHelpdeskCount(301));
            this.domofondGroupHelpDeskCount = gson.toJson(adminService.getGroupFilterHelpdeskCount(301));
        }
    }

    public String getModerGroupHelpDeskCount() {
        return moderGroupHelpDeskCount;
    }

    public String getDomofondGroupHelpDeskCount() {
        return domofondGroupHelpDeskCount;
    }
}
