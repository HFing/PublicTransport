package com.hfing.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hfing.pojo.User;
import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import com.hfing.pojo.User;



@Entity
@Table(name = "routes")
public class Route implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "route_name")
    private String routeName;

    public enum TransportType {
        bus, tram, subway, other
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type")
    private TransportType transportType;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @Column(name = "created_at")
    @JsonIgnore
    private Timestamp createdAt;

    @OneToMany(mappedBy = "route")
    @JsonIgnore
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "route")
    @JsonIgnore
    private Set<RouteStation> routeStations;

    @OneToMany(mappedBy = "route")
    @JsonIgnore
    private Set<FavoriteRoute> favoriteRoutes;

    @OneToMany(mappedBy = "route")
    @JsonIgnore
    private Set<Notification> notifications;

    public Route(Integer routeId, String routeName, TransportType transportType, User createdBy, Timestamp createdAt) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.transportType = transportType;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    public Route() {
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Set<RouteStation> getRouteStations() {
        return routeStations;
    }

    public void setRouteStations(Set<RouteStation> routeStations) {
        this.routeStations = routeStations;
    }

    public Set<FavoriteRoute> getFavoriteRoutes() {
        return favoriteRoutes;
    }

    public void setFavoriteRoutes(Set<FavoriteRoute> favoriteRoutes) {
        this.favoriteRoutes = favoriteRoutes;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}

