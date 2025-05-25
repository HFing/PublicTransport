package com.hfing.repositories.impl;

import com.hfing.pojo.Notification;
import com.hfing.pojo.Route;
import com.hfing.pojo.User;
import com.hfing.repositories.NotificationRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getSession() {
        return factory.getObject().getCurrentSession();
    }

    @Override
    public Notification findByUserAndRoute(int userId, int routeId) {
        String hql = "FROM Notification WHERE user.userId = :userId AND route.routeId = :routeId";
        List<Notification> list = getSession()
                .createQuery(hql, Notification.class)
                .setParameter("userId", userId)
                .setParameter("routeId", routeId)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void save(Notification n) {
        getSession().save(n);
    }

    @Override
    public void update(Notification n) {
        getSession().update(n);
    }

    @Override
    public boolean updateOrInsert(int userId, int routeId, boolean notify) {
        Notification n = findByUserAndRoute(userId, routeId);
        if (n != null) {
            n.setNotifyOnChanges(notify);
            update(n);
        } else {
            Notification newN = new Notification();
            newN.setUser(getSession().get(User.class, userId));
            newN.setRoute(getSession().get(Route.class, routeId));
            newN.setNotifyOnChanges(notify);
            save(newN);
        }
        return true;
    }

    @Override
    public List<Notification> getUsersByRoute(int routeId) {
        String hql = "FROM Notification n WHERE n.route.routeId = :routeId";
        return getSession()
                .createQuery(hql, Notification.class)
                .setParameter("routeId", routeId)
                .getResultList();
    }

}
