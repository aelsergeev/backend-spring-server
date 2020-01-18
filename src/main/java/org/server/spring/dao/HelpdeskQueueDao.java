package org.server.spring.dao;

import org.server.spring.models.HelpdeskQueue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HelpdeskQueueDao extends CrudRepository<HelpdeskQueue, Long> {
    List<HelpdeskQueue> findAll();
}

