package com.hfing.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hfing.pojo.Route;
import com.hfing.pojo.RouteStation;
import com.hfing.pojo.User;
import com.hfing.pojo.dto.RouteDTO;
import com.hfing.pojo.dto.ScheduleDTO;
import com.hfing.pojo.dto.StationDTO;
import com.hfing.repositories.RouteRepository;
import com.hfing.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Override
    public List<RouteDTO> searchRoutes(String from, String to, Date day) {
        List<Route> routes = routeRepo.searchRoutes(from, to, day);
        List<RouteDTO> result = new ArrayList<>();

        for (Route r : routes) {
            List<StationDTO> stationDTOs = r.getRouteStations().stream()
                    .sorted(Comparator.comparing(RouteStation::getStopOrder))
                    .map(rs -> new StationDTO(
                            rs.getStation().getStationName(),
                            rs.getStation().getLatitude(),
                            rs.getStation().getLongitude()
                    )).toList();

            List<ScheduleDTO> scheduleDTOs = r.getSchedules().stream()
                    .map(s -> new ScheduleDTO(
                            s.getDay().toString(),
                            s.getStartTime().toString(),
                            s.getEndTime().toString()
                    )).toList();

            result.add(new RouteDTO(
                    r.getRouteId(),
                    r.getRouteName(),
                    r.getTransportType().toString(),
                    stationDTOs,
                    scheduleDTOs
            ));
        }

        return result;
    }







}

