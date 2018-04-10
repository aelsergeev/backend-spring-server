package ru.server.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.server.spring.configs.properties.WebSocketProperties;
import ru.server.spring.dao.NotificationDao;
import ru.server.spring.models.User;
import ru.server.spring.models.api.NotificationApi;
import ru.server.spring.models.notification.Notification;
import ru.server.spring.models.notification.NotificationUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface NotificationService {
    Notification getNotificationById(UUID uuid);

    List<Notification> list();

    Notification add(Notification notification);
    Notification add(NotificationApi notificationApi);

    NotificationUser updateToRead(UUID notificationUuid, long id);
    NotificationUser updateToRead(UUID notificationUuid, String username);

    List<NotificationUser> listUnreadNotificationByUser(long id);
    List<NotificationUser> listUnreadNotificationByUser(String username);

    List<NotificationUser> listReadNotificationByUser(long id);
    List<NotificationUser> listReadNotificationByUser(String username);
}


@Service
class NotificationServiceImpl implements NotificationService {

    private final static String unreadStatus = "unread";
    private final static String readStatus = "read";

    private final NotificationDao notificationDao;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.notificationDao = notificationDao;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public Notification getNotificationById(UUID uuid) {
        return notificationDao.getNotificationById(uuid);
    }

    public List<Notification> list() {
        return notificationDao.list();
    }

    public List<NotificationUser> listReadNotificationByUser(long id) {
        return notificationDao.listNotificationByUser(id, readStatus);
    }
    public List<NotificationUser> listReadNotificationByUser(String username) {
        return notificationDao.listNotificationByUser(username, readStatus);
    }

    public List<NotificationUser> listUnreadNotificationByUser(long id) {
        return notificationDao.listNotificationByUser(id, unreadStatus);
    }
    public List<NotificationUser> listUnreadNotificationByUser(String username) {
        return notificationDao.listNotificationByUser(username, unreadStatus);
    }

    public NotificationUser updateToRead(UUID notificationUuid, long id) {
        NotificationUser notificationUser = notificationDao.getNotificationUserByPK(notificationUuid, id);

        notificationUser.setStatus(readStatus);

        return notificationDao.update(notificationUser);
    }
    public NotificationUser updateToRead(UUID notificationUuid, String username) {
        NotificationUser notificationUser = notificationDao.getNotificationUserByPK(notificationUuid, username);

        notificationUser.setStatus(readStatus);

        return notificationDao.update(notificationUser);
    }

    @Transactional
    public Notification add(NotificationApi notificationApi) {
        return update(notificationApi.toNotification(userService));
    }

    @Transactional
    public Notification add(Notification notification) {
        return update(notification);
    }

    private Notification update(Notification notification) {
        notification = notificationDao.update(notification);
        sendNotificationToUser(notification);
        return notification;
    }

    private void sendNotificationToUser(Notification notification) {
        Set<NotificationUser> notificationUsers = notification.getToUsers();

        notification.setToUsers(null);

        for (NotificationUser notificationUser : notificationUsers) {
            User user = notificationUser.getUser();

            notificationUser.setNotification(notification);
            notificationUser.setUser(null);

            simpMessagingTemplate.convertAndSendToUser(user.getUsername(), WebSocketProperties.Notification.newSubscription, notificationUser);
        }
    }

}