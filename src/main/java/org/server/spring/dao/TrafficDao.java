package org.server.spring.dao;

import org.server.spring.models.TrafficTemplate;
import org.server.spring.models.TrafficTemplate_;
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
public class TrafficDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TrafficTemplate> listTemplates() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrafficTemplate> criteriaQuery = criteriaBuilder.createQuery(TrafficTemplate.class);

        Root<TrafficTemplate> trafficTemplatesRoot = criteriaQuery.from(TrafficTemplate.class);

        criteriaQuery
                .select(trafficTemplatesRoot)
                .orderBy(criteriaBuilder.asc(trafficTemplatesRoot.get(TrafficTemplate_.templateOrder)));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public TrafficTemplate updateTemplates(TrafficTemplate trafficTemplate) {
        return entityManager.merge(trafficTemplate);
    }

    public void deleteTemplates(TrafficTemplate trafficTemplate) { entityManager.remove(entityManager.merge(trafficTemplate)); }

}
