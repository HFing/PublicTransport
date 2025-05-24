package com.hfing.controllers;

import com.hfing.pojo.Schedule;
import com.hfing.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hfing.pojo.RouteStation;

import java.sql.Date;
import java.util.List;

@Controller
public class AdminTripController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping("/admin/trips")
    public String showTrips(Model model) {
        Date today = new Date(System.currentTimeMillis());
        List<Schedule> trips = scheduleRepository.getSchedulesFromDate(today);
        model.addAttribute("trips", trips);
        return "admin/trip/list";
    }

    @GetMapping("/admin/trip/{id}")
    public String tripDetail(@PathVariable("id") int id, Model model) {
        Schedule trip = scheduleRepository.getScheduleById(id);
        // Get and sort RouteStations by stopOrder
        List<RouteStation> stops = trip.getRoute().getRouteStations()
                .stream()
                .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))
                .toList();
        model.addAttribute("trip", trip);
        model.addAttribute("stops", stops);
        return "admin/trip/detail";
    }
}