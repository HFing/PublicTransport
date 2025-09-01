package com.hfing.controllers;

import com.hfing.pojo.Schedule;
import com.hfing.pojo.Route;
import com.hfing.repositories.ScheduleRepository;
import com.hfing.services.RouteService;
import com.hfing.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.sql.Date;
import java.util.List;

@Controller
public class AdminScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private ScheduleService scheduleService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Time.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    String value = text.trim();
                    if (value.length() == 5) { // "HH:mm"
                        value += ":00";
                    }
                    setValue(Time.valueOf(value));
                }
            }
        });

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    setValue(Date.valueOf(text));
                }
            }
        });

        binder.registerCustomEditor(Route.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    int routeId = Integer.parseInt(text);
                    setValue(routeService.getRouteById(routeId));
                }
            }
        });
    }

    @GetMapping("/admin/schedule")
    public String scheduleView(Model model) {
        List<Schedule> schedules = scheduleRepository.getSchedules();
        model.addAttribute("schedules", schedules);
        return "admin/schedule/show";
    }

    @GetMapping("/admin/schedule/create")
    public String showCreateForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("routes", routeService.getRoutes());
        return "admin/schedule/create";
    }

    @PostMapping("/admin/schedule/create")
    public String createSchedule(@ModelAttribute("schedule") Schedule schedule) {
        scheduleService.addSchedule(schedule);
        return "redirect:/admin/schedule";
    }

    @GetMapping("/admin/schedule/delete/{id}")
    public String confirmDelete(@PathVariable("id") int id, Model model) {
        Schedule schedule = scheduleRepository.getScheduleById(id);
        model.addAttribute("schedule", schedule);
        return "admin/schedule/delete";
    }

    @PostMapping("/admin/schedule/delete")
    public String deleteSchedule(@RequestParam("id") int id) {
        scheduleRepository.deleteSchedule(id);
        return "redirect:/admin/schedule";
    }

    @GetMapping("/admin/schedule/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Schedule schedule = scheduleRepository.getScheduleById(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("routes", routeService.getRoutes());
        return "admin/schedule/update";
    }

    @PostMapping("/admin/schedule/update")
    public String updateSchedule(@ModelAttribute("schedule") Schedule schedule, Model model) {
        try {
            scheduleRepository.updateSchedule(schedule);
            return "redirect:/admin/schedule";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating schedule: " + e.getMessage());
            model.addAttribute("schedule", schedule);
            model.addAttribute("routes", routeService.getRoutes());
            return "admin/schedule/update";
        }
    }
}