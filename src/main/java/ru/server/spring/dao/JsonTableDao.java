package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.JsonTable;

import java.util.List;
import java.util.UUID;

public interface JsonTableDao extends CrudRepository<JsonTable, UUID> {
    List<JsonTable> findAll();
    JsonTable findOne(UUID uuid);
    List<JsonTable> findAllByName(String name);
}
