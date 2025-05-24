package com.hfing.pojo.dto;

import java.util.List;

public class RouteDTO {
    private Integer routeId;
    private String routeName;
    private String transportType;
    private List<StationDTO> stations;
    private List<ScheduleDTO> schedules;

    public RouteDTO(Integer routeId, String routeName, String transportType, List<StationDTO> stations, List<ScheduleDTO> schedules) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.transportType = transportType;
        this.stations = stations;
        this.schedules = schedules;
    }

    // Getters + setters
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

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public List<StationDTO> getStations() {
        return stations;
    }

    public void setStations(List<StationDTO> stations) {
        this.stations = stations;
    }

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }
    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}