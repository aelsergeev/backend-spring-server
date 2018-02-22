package ru.server.spring.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smm_stat")
public class SmmStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username_id")
    private Long usernameId;
    @ManyToOne
    @JoinColumn(name = "username_id", insertable = false, updatable = false)
    private User username;
    @Column(name = "message_id")
    private String messageId;
    @Column(name = "tag_string")
    private String tagString;
    @Column(name = "add_time", insertable = false, updatable = false)
    private Date addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(Long usernameId) {
        this.usernameId = usernameId;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}
