package ru.server.spring.schedulers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.server.spring.configs.SchedulersConfiguration;
import ru.server.spring.dao.CachedDao;
import ru.server.spring.dao.HelpdeskGroupDao;
import ru.server.spring.dao.HelpdeskGroupIdDao;
import ru.server.spring.models.HelpdeskGroup;
import ru.server.spring.models.HelpdeskGroupId;
import ru.server.spring.models.User;
import ru.server.spring.models.api.HelpdeskGroupCount;
import ru.server.spring.services.AdminService;
import ru.server.spring.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class AdminScheduler {

    private final SchedulersConfiguration schedulersConfiguration;
    private final AdminService adminService;
    private final UserService userService;
    private final HelpdeskGroupDao helpdeskGroupDao;
    private final HelpdeskGroupIdDao helpdeskGroupIdDao;
    private final CachedDao cachedDao;

    public AdminScheduler(SchedulersConfiguration schedulersConfiguration, AdminService adminService, UserService userService, HelpdeskGroupDao helpdeskGroupDao, HelpdeskGroupIdDao helpdeskGroupIdDao, CachedDao cachedDao) {
        this.schedulersConfiguration = schedulersConfiguration;
        this.adminService = adminService;
        this.userService = userService;
        this.helpdeskGroupDao = helpdeskGroupDao;
        this.helpdeskGroupIdDao = helpdeskGroupIdDao;
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
            List<HelpdeskGroupCount> list = new ArrayList<>();

            List<HelpdeskGroupId> helpdeskGroupIds = helpdeskGroupIdDao.findAll();
            List<HelpdeskGroup> helpdeskGroups = helpdeskGroupDao.findAll();

            for (HelpdeskGroupId helpdeskGroupId : helpdeskGroupIds) {
                JsonArray hdQueues = adminService.getGroupFilterHelpdeskCount(helpdeskGroupId.getId());

                for (JsonElement hdQueue : hdQueues) {
                    int hdQueueId = hdQueue.getAsJsonObject().get("id").getAsInt();
                    int hdQueueCount = hdQueue.getAsJsonObject().get("count").getAsInt();

                    for (HelpdeskGroup helpdeskGroup : helpdeskGroups)
                        if (helpdeskGroup.getId() == hdQueueId)
                            list.add(new HelpdeskGroupCount(helpdeskGroup, hdQueueCount));
                }
            }

            cachedDao.setGroupHelpDeskCount(list);
        }
    }

}
