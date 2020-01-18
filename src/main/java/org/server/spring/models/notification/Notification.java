package org.server.spring.models.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.server.spring.models.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;
    private String head;
    private String body;
    private String link;
    private Instant time;

    @ManyToOne
    private User fromUser;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.notification", cascade = CascadeType.ALL)
    private Set<NotificationUser> toUsers = new HashSet<>();

    public Notification() {}

    public Notification(String head, String body, String link, User fromUser, Set<User> toUsers) {
        this.head = head;
        this.body = body;
        this.link = link;
        this.fromUser = fromUser;
        this.time = Instant.now();

        for (User user : toUsers) {
            this.toUsers.add(user.toNotificationUser(this));
        }
    }

    public void removeNotificationFromToUsers() {
        for (NotificationUser notificationUser : toUsers)
            notificationUser.setNotification(null);
    }

}
