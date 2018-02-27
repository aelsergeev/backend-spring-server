package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.HelpdeskGroup;

import java.util.List;

public interface HelpdeskGroupDao extends CrudRepository<HelpdeskGroup, Long> {
    List<HelpdeskGroup> findAll();
}

