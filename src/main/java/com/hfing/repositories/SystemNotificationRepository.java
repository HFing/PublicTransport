package com.hfing.repositories;

import com.hfing.pojo.SystemNotification;

import java.util.List;

public interface SystemNotificationRepository {
    void save(SystemNotification n);
    List<SystemNotification> findByUserId(int userId);
    void markAsRead(int notificationId);
}
