package org.server.spring.dao;

import org.server.spring.models.User_;
import org.server.spring.models.notification.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class NotificationDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Notification getNotificationById(UUID uuid) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);

        Root<Notification> notificationRoot = criteriaQuery.from(Notification.class);

        criteriaQuery.where(criteriaBuilder.equal(notificationRoot.get(Notification_.uuid), uuid));

        Notification notification = entityManager.createQuery(criteriaQuery).getSingleResult();

        // TODO change to criteriaQuery.select()
        notification.removeNotificationFromToUsers();

        return notification;
    }

    public NotificationUser getNotificationUserByPK(UUID notificationUuid, long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotificationUser> criteriaQuery = criteriaBuilder.createQuery(NotificationUser.class);

        Root<NotificationUser> notificationUserRoot = criteriaQuery.from(NotificationUser.class);

        criteriaQuery.where(
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.pk).get(NotificationUserPK_.user), userId),
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.pk).get(NotificationUserPK_.notification).get(Notification_.uuid), notificationUuid)
        );

        NotificationUser notificationUser = entityManager.createQuery(criteriaQuery).getSingleResult();

        // TODO change to criteriaQuery.select()
        notificationUser.getNotification().setToUsers(null);

        return notificationUser;
    }

    public NotificationUser getNotificationUserByPK(UUID notificationUuid, String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotificationUser> criteriaQuery = criteriaBuilder.createQuery(NotificationUser.class);

        Root<NotificationUser> notificationUserRoot = criteriaQuery.from(NotificationUser.class);

        criteriaQuery.where(
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.pk).get(NotificationUserPK_.user).get(User_.username), username),
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.pk).get(NotificationUserPK_.notification).get(Notification_.uuid), notificationUuid)
        );

        NotificationUser notificationUser = entityManager.createQuery(criteriaQuery).getSingleResult();

        // TODO change to criteriaQuery.select()
        notificationUser.getNotification().setToUsers(null);

        return notificationUser;
    }

    public List<Notification> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);

        Root<Notification> notificationRoot = criteriaQuery.from(Notification.class);

        criteriaQuery.select(notificationRoot);

        List<Notification> notifications = entityManager.createQuery(criteriaQuery).getResultList();

        // TODO change to criteriaQuery.select()
        for (Notification notification : notifications) notification.removeNotificationFromToUsers();

        return notifications;
    }

    public List<NotificationUser> listNotificationByUser(long id, String status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotificationUser> criteriaQuery = criteriaBuilder.createQuery(NotificationUser.class);

        Root<NotificationUser> notificationUserRoot = criteriaQuery.from(NotificationUser.class);

        criteriaQuery.where(
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.status), status),
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.pk).get(NotificationUserPK_.user), id)
        );

        List<NotificationUser> notificationUsers = entityManager.createQuery(criteriaQuery).getResultList();

        // TODO change to criteriaQuery.select()
        for (NotificationUser notificationUser : notificationUsers) {
            notificationUser.getNotification().setToUsers(null);
            notificationUser.setUser(null);
        }

        return notificationUsers;
    }

    public List<NotificationUser> listNotificationByUser(String username, String status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotificationUser> criteriaQuery = criteriaBuilder.createQuery(NotificationUser.class);

        Root<NotificationUser> notificationUserRoot = criteriaQuery.from(NotificationUser.class);

        criteriaQuery.where(
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.status), status),
                criteriaBuilder.equal(notificationUserRoot.get(NotificationUser_.pk).get(NotificationUserPK_.user).get(User_.username), username)
        );

        List<NotificationUser> notificationUsers = entityManager.createQuery(criteriaQuery).getResultList();

        // TODO change to criteriaQuery.select()
        for (NotificationUser notificationUser : notificationUsers) {
            notificationUser.getNotification().setToUsers(null);
            notificationUser.setUser(null);
        }

        return notificationUsers;
    }

    public NotificationUser update(NotificationUser notificationUser) {
        return entityManager.merge(notificationUser);
    }

    public Notification update(Notification notification) {
        entityManager.persist(notification);
        entityManager.flush();

        notification.removeNotificationFromToUsers();
        return notification;
    }

}
