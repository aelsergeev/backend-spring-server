package org.server.spring.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "intern_log")
public class InternLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "intern_id")
    private Long internId;
    @ManyToOne
    @JoinColumn(name = "intern_id", insertable = false, updatable = false)
    private User intern;
    private String type;
    @Column(name = "item_id")
    private Long itemId;
    private String status;
    private String reason;
    @Column(name = "add_time", insertable = false, updatable = false)
    private Date addTime;

    public User getIntern() {
        return intern;
    }

    public void setIntern(User intern) {
        this.intern = intern;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInternId() {
        return internId;
    }

    public void setInternId(Long internId) {
        this.internId = internId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}
