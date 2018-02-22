package ru.server.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.server.spring.models.notification.Notification;
import ru.server.spring.models.notification.NotificationUser;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private UserShort leader;
    private String surname;
    private String name;
    private String username;
    @JsonIgnore
    @Column(updatable = false, insertable = false)
    private String password;
    private String email;
    private String skype;
    private Long phone;
    private Date birthday;
    private Long adm_user_id;
    private UUID avatar;
    private Instant firstWorkDay;

    @ManyToOne
    private Shift shift;
    @ManyToOne
    private Subdivision subdivision;
    @ManyToOne
    private Weekend weekend;
    @ManyToOne
    private Position position;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;

    public User() {}

    public User(User user) {
        this.id = user.getId();
        this.leader = user.getLeader();
        this.surname = user.getSurname();
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.skype = user.getSkype();
        this.phone = user.getPhone();
        this.birthday = user.getBirthday();
        this.adm_user_id = user.getAdm_user_id();
        this.avatar = user.getAvatar();
        this.firstWorkDay = user.getFirstWorkDay();
        this.shift = user.getShift();
        this.subdivision = user.getSubdivision();
        this.weekend = user.getWeekend();
        this.position = user.getPosition();
        this.permissions = user.getPermissions();
    }

    public User(UserShort userShort) {
        this.id = userShort.getId();
        this.adm_user_id = userShort.getAdm_user_id();
        this.email = userShort.getEmail();
        this.name = userShort.getName();
        this.phone = userShort.getPhone();
        this.surname = userShort.getSurname();
        this.skype = userShort.getSkype();
        this.username = userShort.getUsername();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public Weekend getWeekend() {
        return weekend;
    }

    public void setWeekend(Weekend weekend) {
        this.weekend = weekend;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getAdm_user_id() {
        return adm_user_id;
    }

    public void setAdm_user_id(Long adm_user_id) {
        this.adm_user_id = adm_user_id;
    }

    public UserShort getLeader() {
        return leader;
    }

    public void setLeader(UserShort leader) {
        this.leader = leader;
    }

    public UUID getAvatar() {
        return avatar;
    }

    public void setAvatar(UUID avatar) {
        this.avatar = avatar;
    }

    public Instant getFirstWorkDay() {
        return firstWorkDay;
    }

    public void setFirstWorkDay(Instant firstWorkDay) {
        this.firstWorkDay = firstWorkDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public NotificationUser toNotificationUser(Notification notification) {
        return new NotificationUser(this, notification);
    }

}

