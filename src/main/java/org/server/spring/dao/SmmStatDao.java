package org.server.spring.dao;

import org.server.spring.models.*;
import org.server.spring.models.api.Statistic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class SmmStatDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SmmStat> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SmmStat> criteriaQuery = criteriaBuilder.createQuery(SmmStat.class);

        Root<SmmStat> smmStatRoot = criteriaQuery.from(SmmStat.class);

        criteriaQuery.select(smmStatRoot);

        return entityManager.createQuery(criteriaQuery).setMaxResults(500).getResultList();
    }

    public List<Statistic> stat(Date firstDay, Date secondDay) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Statistic> criteriaQuery = criteriaBuilder.createQuery(Statistic.class);

        Root<SmmStat> smmStatRoot = criteriaQuery.from(SmmStat.class);
        Join<SmmStat, User> userJoin = smmStatRoot.join(SmmStat_.username);
        Join<User, Subdivision> subdivisionJoin = userJoin.join(User_.subdivision);

        criteriaQuery
                .select(
                        criteriaBuilder.construct(
                                Statistic.class,
                                smmStatRoot.get(SmmStat_.usernameId),
                                userJoin.get(User_.username),
                                userJoin.get(User_.surname),
                                userJoin.get(User_.name),
                                subdivisionJoin.get(Subdivision_.divisionName),
                                subdivisionJoin.get(Subdivision_.subdivision),
                                subdivisionJoin.get(Subdivision_.subdivision_name),
                                criteriaBuilder.count(smmStatRoot)
                        ))
                .groupBy(
                        smmStatRoot.get(SmmStat_.usernameId),
                        userJoin.get(User_.username),
                        userJoin.get(User_.surname),
                        userJoin.get(User_.name),
                        subdivisionJoin.get(Subdivision_.divisionName),
                        subdivisionJoin.get(Subdivision_.subdivision),
                        subdivisionJoin.get(Subdivision_.subdivision_name))
                .where(
                        criteriaBuilder.between(smmStatRoot.get(SmmStat_.addTime), firstDay, secondDay)
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public SmmStat update(SmmStat smmStat) {
        return entityManager.merge(smmStat);
    }

}
