package com.hfing.services.impl;

import com.hfing.pojo.Notification;
import com.hfing.pojo.Route;
import com.hfing.pojo.User;
import com.hfing.repositories.NotificationRepository;
import com.hfing.services.NotificationService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepo;

    @Override
    public boolean updateNotifySetting(int userId, int routeId, boolean notify) {
        notificationRepo.updateOrInsert(userId, routeId, notify);
        return true;
    }
}
