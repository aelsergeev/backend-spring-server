package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.HelpdeskQueue;

import java.util.List;

public interface HelpdeskQueueDao extends CrudRepository<HelpdeskQueue, Long> {
    List<HelpdeskQueue> findAll();
}

