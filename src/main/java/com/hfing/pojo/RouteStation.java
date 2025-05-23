package com.hfing.pojo;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "route_stations")
public class RouteStation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "stop_order")
    private Integer stopOrder;

    public RouteStation(Integer id, Route route, Station station, Integer stopOrder) {
        this.id = id;
        this.route = route;
        this.station = station;
        this.stopOrder = stopOrder;
    }
    public RouteStation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }
}