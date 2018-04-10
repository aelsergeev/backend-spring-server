package ru.server.spring.models.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.server.spring.models.User;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class NotificationUser {

    @EmbeddedId
    @JsonIgnore
    private NotificationUserPK pk = new NotificationUserPK();
    private String status;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Notification getNotification() {
        return getPk().getNotification();
    }

    public void setNotification(Notification notification) {
        getPk().setNotification(notification);
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User user) {
        getPk().setUser(user);
    }

    public NotificationUser() {}

    public NotificationUser(User user, Notification notification) {
        this.status = "unread";
        this.setNotification(notification);
        this.setUser(user);
    }

}

@Data
@EqualsAndHashCode(exclude = "notification")
@ToString(exclude = "notification")
@Embeddable
class NotificationUserPK implements Serializable {

    @ManyToOne
    private Notification notification;
    @ManyToOne
    private User user;

}
