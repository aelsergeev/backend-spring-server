package org.server.spring.dao;

import org.server.spring.models.ModStatCategory;
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
public class ModStatCategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ModStatCategory> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ModStatCategory> criteriaQuery = criteriaBuilder.createQuery(ModStatCategory.class);

        Root<ModStatCategory> modStatCategoryRoot = criteriaQuery.from(ModStatCategory.class);

        criteriaQuery.select(modStatCategoryRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void update(ModStatCategory modStatCategory) {
        entityManager.merge(modStatCategory);
    }

}
