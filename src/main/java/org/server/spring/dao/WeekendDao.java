package org.server.spring.dao;

import org.server.spring.models.Weekend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeekendDao extends CrudRepository<Weekend, Long> {
    List<Weekend> findAll();
}
