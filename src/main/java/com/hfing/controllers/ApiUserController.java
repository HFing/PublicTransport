package com.hfing.controllers;

import com.hfing.pojo.Route;
import com.hfing.pojo.Station;
import com.hfing.services.RouteService;
import com.hfing.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @GetMapping("/routes/search")
    public ResponseEntity<?> searchRoutes(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("date") String dateStr) {
        try {
            Date date = Date.valueOf(dateStr);
            return ResponseEntity.ok(routeService.searchRoutes(from.trim(), to.trim(), date));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Sai định dạng ngày. Phải là yyyy-MM-dd");
        }
    }




    @GetMapping("/stations/search")
    public ResponseEntity<List<Station>> searchStations(
            @RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(stationService.searchStations(keyword));
    }
}
