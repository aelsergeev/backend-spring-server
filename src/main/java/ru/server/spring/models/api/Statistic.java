package ru.server.spring.models.api;


public class Statistic {

    private final Long usernameId;
    private final String username;
    private final String surname;
    private final String name;
    private final String division;
    private final String subdivision;
    private final String subdivision_name;
    private final Long sum;

    public Statistic(Long usernameId, String username, String surname, String name, String division, String subdivision, String subdivision_name, Long sum) {
        this.usernameId = usernameId;
        this.username = username;
        this.surname = surname;
        this.name = name;
        this.division = division;
        this.subdivision = subdivision;
        this.subdivision_name = subdivision_name;
        this.sum = sum;
    }

    public Long getUsernameId() {
        return usernameId;
    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public String getSubdivision_name() {
        return subdivision_name;
    }

    public Long getSum() {
        return sum;
    }

}
