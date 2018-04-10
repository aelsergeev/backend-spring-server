package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.Weekend;

import java.util.List;

public interface WeekendDao extends CrudRepository<Weekend, Long> {
    List<Weekend> findAll();
}
