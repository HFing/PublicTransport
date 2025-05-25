package com.hfing.repositories;

import com.hfing.pojo.Notification;

import java.util.List;

public interface NotificationRepository {
    boolean updateOrInsert(int userId, int routeId, boolean notify);
    public void save(Notification n);
    public void update(Notification n);
    public Notification findByUserAndRoute(int userId, int routeId);
    List<Notification> getUsersByRoute(int routeId);
}