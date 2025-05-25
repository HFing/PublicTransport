package com.hfing.repositories;

import com.hfing.pojo.Route;
import java.util.List;
import java.sql.Date;
import java.util.Map;

public interface RouteRepository {
    List<Route> getRoutes();
    Route getRouteById(int id);
    Route addRoute(Route route);
    Route updateRoute(Route route);
    boolean deleteRoute(int id);
    List<Route> searchRoutes(String from, String to, Date day);
    long countRoutes();
    Map<String, Long> countByTransportType();
}
