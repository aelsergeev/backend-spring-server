package org.server.spring.dao;

import org.server.spring.models.TaskLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TaskLogDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TaskLog> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TaskLog> criteriaQuery = criteriaBuilder.createQuery(TaskLog.class);

        Root<TaskLog> taskLogRoot = criteriaQuery.from(TaskLog.class);

        criteriaQuery.select(taskLogRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public TaskLog update(TaskLog taskLog) {
        return entityManager.merge(taskLog);
    }

    public void delete(TaskLog taskLog) { entityManager.remove(entityManager.merge(taskLog)); }

}
