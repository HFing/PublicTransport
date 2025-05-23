package com.hfing.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hfing.pojo.Route;
import com.hfing.pojo.User;
import com.hfing.repositories.RouteRepository;
import com.hfing.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepo;

    @Override
    public List<Route> getRoutes() {
        return this.routeRepo.getRoutes();
    }

    @Override
    public Route getRouteById(int id) {
        return this.routeRepo.getRouteById(id);
    }

    @Override
    public Route addRoute(Route route) {
        route.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return this.routeRepo.addRoute(route);
    }

    @Override
    public Route updateRoute(Route route) {
        return this.routeRepo.updateRoute(route);
    }

    @Override
    public boolean deleteRoute(int id) {
        return this.routeRepo.deleteRoute(id);
    }

    @Override
    public List<Route> getAllRoutes() {
        return this.routeRepo.getRoutes();
    }

    @Override
    public Route saveRoute(Route route) {
        return this.routeRepo.addRoute(route);
    }

}

