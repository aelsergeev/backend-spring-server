package ru.server.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.server.spring.models.InternLog;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

public interface InternLogDao extends CrudRepository<InternLog, Long>, InternLogDaoCustom {
    List<InternLog> findAll();
}

interface InternLogDaoCustom {
    List<InternLog> list();
    InternLog update(InternLog internLog);
}

@Transactional
class InternLogDaoImpl implements InternLogDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<InternLog> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<InternLog> criteriaQuery = criteriaBuilder.createQuery(InternLog.class);

        Root<InternLog> internLogRoot = criteriaQuery.from(InternLog.class);

        criteriaQuery.select(internLogRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public InternLog update(InternLog internLog) {
        return entityManager.merge(internLog);
    }

}
