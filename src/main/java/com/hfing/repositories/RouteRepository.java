package com.hfing.repositories;

import com.hfing.pojo.Route;
import java.util.List;

public interface RouteRepository {
    List<Route> getRoutes();
    Route getRouteById(int id);
    Route addRoute(Route route);
    Route updateRoute(Route route);
    boolean deleteRoute(int id);
}
