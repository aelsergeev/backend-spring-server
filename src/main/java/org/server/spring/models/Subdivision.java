package org.server.spring.models;

import javax.persistence.*;

@Entity
public class Subdivision {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "division_name")
    private String divisionName;
    private String subdivision;
    private String subdivision_name;
    private String teamlead;
    private String sub_color;
    private String teamlead_login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getSubdivision_name() {
        return subdivision_name;
    }

    public void setSubdivision_name(String subdivision_name) {
        this.subdivision_name = subdivision_name;
    }

    public String getTeamlead() {
        return teamlead;
    }

    public void setTeamlead(String teamlead) {
        this.teamlead = teamlead;
    }

    public String getSub_color() {
        return sub_color;
    }

    public void setSub_color(String sub_color) {
        this.sub_color = sub_color;
    }

    public String getTeamlead_login() {
        return teamlead_login;
    }

    public void setTeamlead_login(String teamlead_login) {
        this.teamlead_login = teamlead_login;
    }

}
