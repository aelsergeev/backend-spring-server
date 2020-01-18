package org.server.spring.dao;

import org.server.spring.models.HelpdeskGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HelpdeskGroupDao extends CrudRepository<HelpdeskGroup, Long> {
    List<HelpdeskGroup> findAll();
}


