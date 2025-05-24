package com.hfing.controllers;

import com.hfing.pojo.Route;
import com.hfing.pojo.Station;
import com.hfing.pojo.User;
import com.hfing.services.RouteService;
import com.hfing.services.StationService;
import com.hfing.services.UserService;
import com.hfing.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);


    @Autowired
    private UserService userDetailsService;

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

    @PostMapping(path = "/users",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestParam Map    <String, String> params, @RequestParam(value = "avatar") MultipartFile avatar) {
        return new ResponseEntity<>(this.userDetailsService.addUser(params, avatar), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {

        if (this.userDetailsService.authenticate(u.getEmail(), u.getPassword())) {
            try {
                String token = JwtUtils.generateToken(u.getEmail());
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {

        return new ResponseEntity<>(this.userDetailsService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

}
