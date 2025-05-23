package com.hfing.controllers;

import com.hfing.pojo.Route;
import com.hfing.pojo.User;
import com.hfing.services.RouteService;
import com.hfing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping("/admin/route")
public class AdminRouteController {
    @Autowired
    private RouteService routeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listRoutes(Model model) {
        model.addAttribute("routes", routeService.getAllRoutes());
        return "admin/route/show";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("route", new Route());
        return "admin/route/create";
    }

    @PostMapping("/create")
    public String createRoute(@ModelAttribute Route route, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        route.setCreatedBy(currentUser);
        route.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        routeService.saveRoute(route);
        return "redirect:/admin/route";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);
        return "admin/route/update";
    }

    @PostMapping("/update")
    public String updateRoute(@ModelAttribute Route route) {
        routeService.updateRoute(route);
        return "redirect:/admin/route";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable("id") int id) {
        routeService.deleteRoute(id);
        return "redirect:/admin/route";
    }
}
