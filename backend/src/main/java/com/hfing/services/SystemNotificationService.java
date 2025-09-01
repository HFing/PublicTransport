package com.hfing.services;

import com.hfing.pojo.SystemNotification;
import java.util.List;

public interface SystemNotificationService {
    void createNotification(SystemNotification n);
    List<SystemNotification> getNotificationsByUser(int userId);
    void markAsRead(int notificationId);
}
