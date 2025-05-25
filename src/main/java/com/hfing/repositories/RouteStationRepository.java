package com.hfing.repositories;

import com.hfing.pojo.RouteStation;
import java.util.List;
import java.util.Map;

public interface  RouteStationRepository {
    RouteStation save(RouteStation routeStation);
    List<RouteStation> getAll();
    RouteStation getById(int id);
    RouteStation update(RouteStation routeStation);
    boolean delete(int id);
    List<RouteStation> getByRouteId(int routeId);

}
