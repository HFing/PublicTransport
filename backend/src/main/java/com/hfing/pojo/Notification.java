package com.hfing.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.hfing.pojo.User;


@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "notify_on_changes")
    private Boolean notifyOnChanges;

    public Notification(Integer notificationId, User user, Route route, Boolean notifyOnChanges) {
        this.notificationId = notificationId;
        this.user = user;
        this.route = route;
        this.notifyOnChanges = notifyOnChanges;
    }
    public Notification() {
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Boolean getNotifyOnChanges() {
        return notifyOnChanges;
    }

    public void setNotifyOnChanges(Boolean notifyOnChanges) {
        this.notifyOnChanges = notifyOnChanges;
    }
}