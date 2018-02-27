package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.HelpdeskGroupId;

import java.util.List;

public interface HelpdeskGroupIdDao extends CrudRepository<HelpdeskGroupId, Long> {
    List<HelpdeskGroupId> findAll();
}


