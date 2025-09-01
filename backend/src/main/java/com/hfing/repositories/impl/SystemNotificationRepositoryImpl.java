package com.hfing.repositories.impl;

import com.hfing.pojo.SystemNotification;
import com.hfing.repositories.SystemNotificationRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
@Transactional
public class SystemNotificationRepositoryImpl implements SystemNotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return factory.getObject().getCurrentSession();
    }

    @Override
    public void save(SystemNotification n) {
        getCurrentSession().save(n);
    }

    @Override
    public List<SystemNotification> findByUserId(int userId) {
        String hql = "FROM SystemNotification WHERE user.userId = :userId ORDER BY createdAt DESC";
        return getCurrentSession().createQuery(hql, SystemNotification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void markAsRead(int notificationId) {
        SystemNotification n = getCurrentSession().get(SystemNotification.class, notificationId);
        if (n != null)
            n.setRead(true);
    }
}
