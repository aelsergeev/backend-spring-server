package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.server.spring.configs.properties.WebSocketProperties;
import ru.server.spring.models.User;
import ru.server.spring.models.api.NotificationApi;
import ru.server.spring.models.notification.Notification;
import ru.server.spring.models.notification.NotificationUser;
import ru.server.spring.services.NotificationService;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/list")
    public List<Notification> listNotification() {
        return notificationService.list();
    }

    @GetMapping("/{uuid}")
    public Notification getNotificationById(@PathVariable UUID uuid) {
        return notificationService.getNotificationById(uuid);
    }

    @GetMapping("/list/user/{id}/unread")
    public List<NotificationUser> listUnreadNotificationByUser(@PathVariable long id) {
        return notificationService.listUnreadNotificationByUser(id);
    }

    @GetMapping("/list/user/{id}/read")
    public List<NotificationUser> listReadNotificationByUser(@PathVariable long id) {
        return notificationService.listReadNotificationByUser(id);
    }

    @PutMapping("/add")
    public Notification addNotification(@RequestBody NotificationApi notificationApi) {
        return notificationService.add(notificationApi);
    }

    @PutMapping("/update/read")
    public NotificationUser updateToRead(@RequestParam UUID notificationUuid, @RequestParam long userId) {
        return notificationService.updateToRead(notificationUuid, userId);
    }

    /**
     * Spring WebSocket not working with annotation {@code @Transactional} for {@link EntityManager} like Spring MVC,
     * that's why models {@link User} and {@link Notification} have type {@code FetchType.EAGER} for annotation
     * {@code @OneToMany} and {@code @ManyToMany}. I choose this way, because it's easy way without many code changing.
     * <p>If you want, you can choose another way, and change <code>@PersistenceContext EntityManager</code>
     * to <code>@PersistenceUnit EntityManagerFactory</code> and add transactions in methods using functions.
     *
     * @author Andrew Sergeev
     * @see ru.server.spring.dao.NotificationDao
     * @see Notification
     * @see User
     */

    @Controller
    @MessageMapping("/notification")
    class NotificationWebSocketController {

        @MessageMapping("/unread")
        @SendToUser(WebSocketProperties.Notification.newSubscription)
        public List<NotificationUser> listUnreadNotificationByUser(Principal principal) {
            return notificationService.listUnreadNotificationByUser(principal.getName());
        }

        @MessageMapping("/update/read")
        @SendToUser(WebSocketProperties.Notification.updateSubscription)
        public NotificationUser updateToRead(String s, Principal principal) {
            return notificationService.updateToRead(UUID.fromString(s), principal.getName());
        }

        @MessageExceptionHandler
        @SendToUser(WebSocketProperties.error)
        public String handleException(Throwable exception) {
            return exception.getMessage();
        }

    }

}
