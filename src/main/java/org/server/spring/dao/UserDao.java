package org.server.spring.dao;

import org.server.spring.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(userRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<User> getUsers(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        Join<User, Weekend> weekendJoin = userRoot.join(User_.weekend, JoinType.LEFT);
        Join<User, Shift> shiftJoin = userRoot.join(User_.shift, JoinType.LEFT);
        Join<User, Position> positionJoin = userRoot.join(User_.position, JoinType.LEFT);
        Join<User, Subdivision> subdivisionJoin = userRoot.join(User_.subdivision, JoinType.LEFT);

        Predicate predicate = criteriaBuilder.conjunction();

        if (user.getId() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.id), user.getId()));
        if (user.getUsername() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.username), user.getUsername()));
        if (user.getName() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.name), user.getName()));
        if (user.getSurname() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.surname), user.getSurname()));
        if (user.getEmail() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.email), user.getEmail()));
        if (user.getSkype() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.skype), user.getSkype()));
        if (user.getPhone() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.phone), user.getPhone()));
        if (user.getBirthday() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.birthday), user.getBirthday()));
        if (user.getAdm_user_id() != null) predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get(User_.adm_user_id), user.getAdm_user_id()));
        if (user.getWeekend() != null) {
            if (user.getWeekend().getId() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(weekendJoin.get(Weekend_.id), user.getWeekend().getId()));
            if (user.getWeekend().getWeekend() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(weekendJoin.get(Weekend_.weekend), user.getWeekend().getWeekend()));
        }
        if (user.getShift() != null) {
            if (user.getShift().getId() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(shiftJoin.get(Shift_.id), user.getShift().getId()));
            if (user.getShift().getShift() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(shiftJoin.get(Shift_.shift), user.getShift().getShift()));
        }
        if (user.getPosition() != null) {
            if (user.getPosition().getId() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(positionJoin.get(Position_.id), user.getPosition().getId()));
            if (user.getPosition().getName() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(positionJoin.get(Position_.name), user.getPosition().getName()));
            if (user.getPosition().getTag() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(positionJoin.get(Position_.tag), user.getPosition().getTag()));
            if (user.getPosition().getLogo() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(positionJoin.get(Position_.logo), user.getPosition().getLogo()));
            if (user.getPosition().getBackgroundColor() != null)
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.equal(positionJoin.get(Position_.backgroundColor), user.getPosition().getBackgroundColor()));
            if (user.getPosition().getTextColor() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(positionJoin.get(Position_.textColor), user.getPosition().getTextColor()));
        }
        if (user.getSubdivision() != null) {
            if (user.getSubdivision().getId() != null)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.id), user.getSubdivision().getId()));
            if (user.getSubdivision().getDivisionName() != null)
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.divisionName), user.getSubdivision().getDivisionName()));
            if (user.getSubdivision().getSubdivision() != null)
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.subdivision), user.getSubdivision().getSubdivision()));
            if (user.getSubdivision().getSubdivision_name() != null)
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.subdivision_name), user.getSubdivision().getSubdivision_name()));
            if (user.getSubdivision().getTeamlead_login() != null)
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.teamlead_login), user.getSubdivision().getTeamlead_login()));
            if (user.getSubdivision().getTeamlead() != null)
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.equal(subdivisionJoin.get(Subdivision_.teamlead), user.getSubdivision().getTeamlead()));
        }

        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public User update(User user) {
        return entityManager.merge(user);
    }

    public User getUserById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get(User_.id), id));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public User getUserByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get(User_.username), username));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<User> getUsersWithNullAdmUserId() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.isNull(userRoot.get(User_.adm_user_id)),
                criteriaBuilder.isNotNull(userRoot.get(User_.email))
        ));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void delete(User user) {
        entityManager.remove(entityManager.merge(user));
    }

}
