package ru.server.spring.dao;

import org.springframework.stereotype.Repository;
import ru.server.spring.models.assistant.AssistantGraphVertex;
import ru.server.spring.models.assistant.AssistantGraphVertex_;
import ru.server.spring.models.assistant.AssistantGraphs;
import ru.server.spring.models.assistant.AssistantGraphs_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class AssistantDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AssistantGraphVertex> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssistantGraphVertex> criteriaQuery = criteriaBuilder.createQuery(AssistantGraphVertex.class);

        Root<AssistantGraphVertex> assistantGraphInfoRoot = criteriaQuery.from(AssistantGraphVertex.class);

        criteriaQuery.select(assistantGraphInfoRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    public List<AssistantGraphVertex> graphInfoListByGraphUuid(UUID graph) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssistantGraphVertex> criteriaQuery = criteriaBuilder.createQuery(AssistantGraphVertex.class);

        Root<AssistantGraphVertex> assistantGraphInfoRoot = criteriaQuery.from(AssistantGraphVertex.class);
        Join<AssistantGraphVertex, AssistantGraphs> assistantGraphsJoin = assistantGraphInfoRoot.join(AssistantGraphVertex_.assistantGraphs);

        criteriaQuery.where(criteriaBuilder.equal(assistantGraphsJoin.get(AssistantGraphs_.uuid), graph));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public AssistantGraphVertex graphInfoHeadByGraphUuid(UUID graph) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssistantGraphVertex> criteriaQuery = criteriaBuilder.createQuery(AssistantGraphVertex.class);

        Root<AssistantGraphVertex> assistantGraphInfoRoot = criteriaQuery.from(AssistantGraphVertex.class);
        Join<AssistantGraphVertex, AssistantGraphs> assistantGraphsJoin = assistantGraphInfoRoot.join(AssistantGraphVertex_.assistantGraphs);

        criteriaQuery.where(criteriaBuilder.equal(assistantGraphsJoin.get(AssistantGraphs_.uuid), graph));

        List<AssistantGraphVertex> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        int count = 0;
        AssistantGraphVertex ans = new AssistantGraphVertex();
        for (AssistantGraphVertex result : resultList)
            if (result.getParents().isEmpty()) {
                ans = result;
                ++count;
            }
        if (count == 1) return ans;
        else return null;
    }

    public AssistantGraphVertex graphInfoByUuid(UUID uuid) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssistantGraphVertex> criteriaQuery = criteriaBuilder.createQuery(AssistantGraphVertex.class);

        Root<AssistantGraphVertex> assistantGraphInfoRoot = criteriaQuery.from(AssistantGraphVertex.class);

        criteriaQuery.where(criteriaBuilder.equal(assistantGraphInfoRoot.get(AssistantGraphVertex_.uuid), uuid));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<AssistantGraphs> graphsList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssistantGraphs> criteriaQuery = criteriaBuilder.createQuery(AssistantGraphs.class);

        Root<AssistantGraphs> assistantGraphInfoRoot = criteriaQuery.from(AssistantGraphs.class);

        criteriaQuery.select(assistantGraphInfoRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public AssistantGraphVertex update(AssistantGraphVertex assistantGraphVertex) {
        return entityManager.merge(assistantGraphVertex);
    }
    public AssistantGraphs update(AssistantGraphs assistantGraphs) {
        return entityManager.merge(assistantGraphs);
    }

    public void delete(AssistantGraphVertex assistantGraphVertex) { entityManager.remove(entityManager.merge(assistantGraphVertex)); }
    public void delete(AssistantGraphs assistantGraphs) { entityManager.remove(entityManager.merge(assistantGraphs)); }

}
