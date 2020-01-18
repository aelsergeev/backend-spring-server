package org.server.spring.schedulers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.server.spring.configs.SchedulersConfiguration;
import org.server.spring.dao.CachedDao;
import org.server.spring.dao.HelpdeskGroupDao;
import org.server.spring.dao.HelpdeskQueueDao;
import org.server.spring.models.HelpdeskGroup;
import org.server.spring.models.HelpdeskQueue;
import org.server.spring.models.User;
import org.server.spring.models.api.HelpdeskQueueApi;
import org.server.spring.services.AdminService;
import org.server.spring.services.UserService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class AdminScheduler {

    private final SchedulersConfiguration schedulersConfiguration;
    private final AdminService adminService;
    private final UserService userService;
    private final HelpdeskQueueDao helpdeskQueueDao;
    private final HelpdeskGroupDao helpdeskGroupDao;
    private final CachedDao cachedDao;

    public AdminScheduler(SchedulersConfiguration schedulersConfiguration, AdminService adminService, UserService userService, HelpdeskQueueDao helpdeskQueueDao, HelpdeskGroupDao helpdeskGroupDao, CachedDao cachedDao) {
        this.schedulersConfiguration = schedulersConfiguration;
        this.adminService = adminService;
        this.userService = userService;
        this.helpdeskQueueDao = helpdeskQueueDao;
        this.helpdeskGroupDao = helpdeskGroupDao;
        this.cachedDao = cachedDao;
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
            List<HelpdeskQueueApi> list = new ArrayList<>();

            List<HelpdeskGroup> helpdeskGroups = helpdeskGroupDao.findAll();
            List<HelpdeskQueue> helpdeskQueues = helpdeskQueueDao.findAll();

            for (HelpdeskGroup helpdeskGroup : helpdeskGroups) {
                JsonObject response = adminService.getGroupFilterHelpdeskCount(helpdeskGroup.getId());
                JsonArray hdQueues = response.getAsJsonArray("result");

                for (JsonElement hdQueue : hdQueues) {
                    int hdQueueId = hdQueue.getAsJsonObject().get("id").getAsInt();
                    int hdQueueCount = hdQueue.getAsJsonObject().get("count").getAsInt();

                    for (HelpdeskQueue helpdeskQueue : helpdeskQueues)
                        if (helpdeskQueue.getId() == hdQueueId)
                            list.add(new HelpdeskQueueApi(helpdeskQueue, hdQueueCount));
                }
            }

            cachedDao.setGroupHelpDeskCount(list);
        }
    }

}
