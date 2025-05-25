package com.hfing.services.impl;

import com.hfing.pojo.SystemNotification;
import com.hfing.repositories.SystemNotificationRepository;
import com.hfing.services.SystemNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemNotificationServiceImpl implements SystemNotificationService {

    @Autowired
    private SystemNotificationRepository systemNotificationRepo;

    @Override
    public void createNotification(SystemNotification n) {
        systemNotificationRepo.save(n);
    }

    @Override
    public List<SystemNotification> getNotificationsByUser(int userId) {
        return systemNotificationRepo.findByUserId(userId);
    }

    @Override
    public void markAsRead(int notificationId) {
        systemNotificationRepo.markAsRead(notificationId);
    }
}
