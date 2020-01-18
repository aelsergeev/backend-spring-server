package org.server.spring.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class JsonTable {

    @Id
    private UUID uuid;
    private String name;
    private String json;

}
