package com.hfing.services;

import com.hfing.pojo.Route;
import com.hfing.pojo.dto.RouteDTO;

import java.sql.Date;
import java.util.List;

public interface RouteService {
    List<Route> getRoutes();
    List<Route> getAllRoutes();
    Route getRouteById(int id);
    Route addRoute(Route route);
    Route saveRoute(Route route);
    Route updateRoute(Route route);
    boolean deleteRoute(int id);
    public List<RouteDTO> searchRoutes(String from, String to, Date day);

}
