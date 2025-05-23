package com.hfing.services;

import com.hfing.pojo.RouteStation;
import java.util.List;

public interface  RouteStationService {
    RouteStation save(RouteStation rs);
    List<RouteStation> getAll();
    RouteStation getById(int id);
    RouteStation update(RouteStation rs);
    boolean delete(int id);
}
