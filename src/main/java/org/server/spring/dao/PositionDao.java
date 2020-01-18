package org.server.spring.dao;

import org.server.spring.models.Position;
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
public class PositionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Position> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Position> criteriaQuery = criteriaBuilder.createQuery(Position.class);

        Root<Position> positionRoot = criteriaQuery.from(Position.class);

        criteriaQuery.select(positionRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Position update(Position position) {
        return entityManager.merge(position);
    }

    public void delete(Position position) { entityManager.remove(entityManager.merge(position)); }

}
