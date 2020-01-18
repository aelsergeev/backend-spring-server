package org.server.spring.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserShort {

    @Id
    private Long id;
    private String surname;
    private String name;
    private String username;
    private String email;
    private String skype;
    private Long phone;
    private Long adm_user_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSkype() {
        return skype;
    }

    public Long getPhone() {
        return phone;
    }

    public Long getAdm_user_id() {
        return adm_user_id;
    }

}
