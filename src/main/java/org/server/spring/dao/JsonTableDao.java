package org.server.spring.dao;

import org.server.spring.models.JsonTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface JsonTableDao extends CrudRepository<JsonTable, UUID> {
    List<JsonTable> findAll();
    List<JsonTable> findAllByName(String name);
}
