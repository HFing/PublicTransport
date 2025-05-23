package com.hfing.controllers;

import com.hfing.pojo.RouteStation;
import com.hfing.services.RouteStationService;
import com.hfing.services.RouteService;
import com.hfing.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/route-station")
public class AdminRouteStationController {

    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("routeStations", routeStationService.getAll());
        return "admin/route-station/show";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("routeStation", new RouteStation());
        model.addAttribute("routes", routeService.getRoutes());
        model.addAttribute("stations", stationService.getAllStations());
        return "admin/route-station/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam("routeId") int routeId,
                         @RequestParam("stationId") int stationId,
                         @RequestParam("stopOrder") int stopOrder) {

        RouteStation rs = new RouteStation();
        rs.setRoute(routeService.getRouteById(routeId));
        rs.setStation(stationService.getStationById(stationId));
        rs.setStopOrder(stopOrder);

        routeStationService.save(rs);
        return "redirect:/admin/route-station";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("routeStation", routeStationService.getById(id));
        model.addAttribute("routes", routeService.getRoutes());
        model.addAttribute("stations", stationService.getAllStations());
        return "admin/route-station/update";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute RouteStation rs) {
        routeStationService.update(rs);
        return "redirect:/admin/route-station";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable("id") int id, Model model) {
        RouteStation rs = routeStationService.getById(id);
        model.addAttribute("routeStation", rs);
        return "admin/route-station/delete"; // đúng path file
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        routeStationService.delete(id);
        return "redirect:/admin/route-station";
    }

}
