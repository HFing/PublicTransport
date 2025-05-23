package com.hfing.controllers;

import com.hfing.pojo.Station;
import com.hfing.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/station")
public class AdminStationController {
    @Autowired
    private StationService stationService;

    @GetMapping
    public String listStations(Model model) {
        model.addAttribute("stations", stationService.getAllStations());
        return "admin/station/show";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("station", new Station());
        return "admin/station/create";
    }

    @PostMapping("/create")
    public String createStation(@ModelAttribute Station station) {
        stationService.saveStation(station);
        return "redirect:/admin/station";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Station station = stationService.getStationById(id);
        model.addAttribute("station", station);
        return "admin/station/update";
    }

    @PostMapping("/update")
    public String updateStation(@ModelAttribute Station station) {
        stationService.saveStation(station);
        return "redirect:/admin/station";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirm(@PathVariable("id") int id, Model model) {
        Station station = stationService.getStationById(id);
        model.addAttribute("station", station);
        return "admin/station/delete";
    }

    @PostMapping("/delete")
    public String deleteStation(@RequestParam("id") int id) {
        stationService.deleteStation(id);
        return "redirect:/admin/station";
    }

    @GetMapping("/{id}")
    public String viewDetail(@PathVariable("id") int id, Model model) {
        Station station = stationService.getStationById(id);
        model.addAttribute("station", station);
        return "admin/station/detail";
    }
}
