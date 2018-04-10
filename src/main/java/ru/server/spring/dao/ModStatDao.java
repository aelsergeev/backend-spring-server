package ru.server.spring.dao;

import org.springframework.stereotype.Repository;
import ru.server.spring.models.*;
import ru.server.spring.models.api.Statistic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ModStatDao {

    @PersistenceContext
    private EntityManager entityManager;

    public ModStat update(ModStat modStat) {
        return entityManager.merge(modStat);
    }

    public void clear(Date date) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<ModStat> criteriaDelete = criteriaBuilder.createCriteriaDelete(ModStat.class);

        Root<ModStat> modStatRoot = criteriaDelete.from(ModStat.class);

        criteriaDelete.where(criteriaBuilder.lessThan(modStatRoot.get(ModStat_.addTime), date));

        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    public List<ModStat> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ModStat> criteriaQuery = criteriaBuilder.createQuery(ModStat.class);

        Root<ModStat> modStatRoot = criteriaQuery.from(ModStat.class);

        criteriaQuery.select(modStatRoot);

        return entityManager.createQuery(criteriaQuery).setMaxResults(500).getResultList();
    }

    public List<Statistic> stat(String category, Date firstDay, Date secondDay) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Statistic> criteriaQuery = criteriaBuilder.createQuery(Statistic.class);

        Root<ModStat> modStatRoot = criteriaQuery.from(ModStat.class);
        Join<ModStat, User> userJoin = modStatRoot.join(ModStat_.username);
        Join<User, Subdivision> subdivisionJoin = userJoin.join(User_.subdivision);

        criteriaQuery
                .select(
                        criteriaBuilder.construct(
                                Statistic.class,
                                modStatRoot.get(ModStat_.usernameId),
                                userJoin.get(User_.username),
                                userJoin.get(User_.surname),
                                userJoin.get(User_.name),
                                subdivisionJoin.get(Subdivision_.divisionName),
                                subdivisionJoin.get(Subdivision_.subdivision),
                                subdivisionJoin.get(Subdivision_.subdivision_name),
                                criteriaBuilder.sum(modStatRoot.get(ModStat_.count))
                        ))
                .groupBy(
                        modStatRoot.get(ModStat_.usernameId),
                        userJoin.get(User_.username),
                        userJoin.get(User_.surname),
                        userJoin.get(User_.name),
                        subdivisionJoin.get(Subdivision_.divisionName),
                        subdivisionJoin.get(Subdivision_.subdivision),
                        subdivisionJoin.get(Subdivision_.subdivision_name))
                .where(
                        criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.subdivision), category),
                        criteriaBuilder.between(modStatRoot.get(ModStat_.addTime), firstDay, secondDay)
                );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
