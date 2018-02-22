package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.Shift;

import java.util.List;

public interface ShiftDao extends CrudRepository<Shift, Long> {
    List<Shift> findAll();
}
