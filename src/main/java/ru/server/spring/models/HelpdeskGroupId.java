package ru.server.spring.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class HelpdeskGroupId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
