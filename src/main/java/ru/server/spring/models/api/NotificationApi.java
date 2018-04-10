package ru.server.spring.models.api;

import lombok.Data;
import ru.server.spring.exceptions.NotificationException;
import ru.server.spring.models.Position;
import ru.server.spring.models.Subdivision;
import ru.server.spring.models.User;
import ru.server.spring.models.notification.Notification;
import ru.server.spring.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Data
public class NotificationApi {

    private String head;
    private String body;
    private String link;
    private Long from;
    private String type;
    private Set<String> to;

    public Notification toNotification(UserService userService) {
        User fromUser = userService.getUserById(from);

        Set<User> toUsers = new HashSet<>();
        switch (type) {
            case "username":
                for (String toUsername : to) {
                    User user = new User();
                    user.setUsername(toUsername);

                    toUsers.addAll(userService.getUsers(user));
                }
                break;
            case "subdivision":
                for (String toSubdivision : to) {
                    User user = new User();
                    Subdivision subdivision = new Subdivision();

                    subdivision.setSubdivision(toSubdivision);
                    user.setSubdivision(subdivision);

                    toUsers.addAll(userService.getUsers(user));
                }
                break;
            case "position":
                for (String toPosition : to) {
                    User user = new User();
                    Position position = new Position();

                    position.setName(toPosition);
                    user.setPosition(position);

                    toUsers.addAll(userService.getUsers(user));
                }
                break;
            case "adm-user-id":
                for (String toAdmUserId : to) {
                    User user = new User();

                    user.setAdm_user_id(Long.parseLong(toAdmUserId));

                    toUsers.addAll(userService.getUsers(user));
                }
                break;
            default:
                throw new NotificationException("type *" + type+ "* does not exist");
        }

        return new Notification(head, body, link, fromUser, toUsers);
    }

}
