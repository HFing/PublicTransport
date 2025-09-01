package com.hfing.services.impl;

import com.hfing.pojo.RouteStation;
import com.hfing.repositories.RouteStationRepository;
import com.hfing.services.RouteStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteStationServiceImpl implements RouteStationService {

    @Autowired
    private RouteStationRepository routeStationRepo;

    @Override
    public RouteStation save(RouteStation rs) {
        return routeStationRepo.save(rs);
    }

    @Override
    public List<RouteStation> getAll() {
        return routeStationRepo.getAll();
    }

    @Override
    public RouteStation getById(int id) {
        return routeStationRepo.getById(id);
    }

    @Override
    public RouteStation update(RouteStation rs) {
        return routeStationRepo.update(rs);
    }

    @Override
    public boolean delete(int id) {
        return routeStationRepo.delete(id);
    }

    @Override
    public List<RouteStation> getByRouteId(int routeId) {
        return routeStationRepo.getByRouteId(routeId);
    }
}

