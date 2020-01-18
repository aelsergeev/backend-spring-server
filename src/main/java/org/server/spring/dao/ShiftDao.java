package org.server.spring.dao;

import org.server.spring.models.Shift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShiftDao extends CrudRepository<Shift, Long> {
    List<Shift> findAll();
}
