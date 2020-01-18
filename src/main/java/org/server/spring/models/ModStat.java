package org.server.spring.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mod_stat")
public class ModStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username_id")
    private Long usernameId;
    @ManyToOne
    @JoinColumn(name = "username_id", insertable = false, updatable = false)
    private User username;
    private String type;
    @Column(name = "items_id")
    private String itemsId;
    private String reason;
    private Long count;
    @Column(name = "addtime", insertable = false, updatable = false)
    private Date addTime;

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemsId() {
        return itemsId;
    }

    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}
