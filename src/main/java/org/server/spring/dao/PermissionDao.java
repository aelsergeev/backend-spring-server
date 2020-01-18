package org.server.spring.dao;

import org.server.spring.models.Permission;
import org.server.spring.models.Permission_;
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
public class PermissionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Permission> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permission> criteriaQuery = criteriaBuilder.createQuery(Permission.class);

        Root<Permission> permissionRoot = criteriaQuery.from(Permission.class);

        criteriaQuery
                .select(permissionRoot)
                .orderBy(criteriaBuilder.asc(permissionRoot.get(Permission_.name)));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Permission update(Permission permission) {
        return entityManager.merge(permission);
    }

    public void delete(Permission permission) { entityManager.remove(entityManager.merge(permission)); }

}
