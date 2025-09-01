package com.hfing.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hfing.pojo.Route;
import com.hfing.pojo.Station;
import com.hfing.pojo.SystemNotification;
import com.hfing.pojo.User;
import com.hfing.repositories.SystemNotificationRepository;
import com.hfing.services.*;
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
    private NotificationService notificationService;

    @Autowired
    private TrafficJamReportService trafficService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteRouteService favoriteRouteService;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @Autowired
    private SystemNotificationRepository systemNotificationRepository;

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


    @PostMapping("/secure/favorite-routes/add")
    public ResponseEntity<?> addFavoriteRoute(
            @RequestParam("routeId") Integer routeId,
            Principal principal) {
        try {
            User user = this.userDetailsService.getUserByUsername(principal.getName());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không hợp lệ");
            }
            favoriteRouteService.addFavorite(user.getUserId(), routeId);
            return ResponseEntity.ok("Đã thêm tuyến đường vào danh sách yêu thích");
        } catch (RuntimeException e) {
            logger.error("Error adding favorite route", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/secure/favorite-routes/remove")
    public ResponseEntity<?> removeFavoriteRoute(
            @RequestParam("routeId") Integer routeId,
            Principal principal) {
        try {
            User user = this.userDetailsService.getUserByUsername(principal.getName());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không hợp lệ");
            }
            favoriteRouteService.removeFavorite(user.getUserId(), routeId);
            return ResponseEntity.ok("Đã xóa tuyến đường khỏi danh sách yêu thích");
        } catch (RuntimeException e) {
            logger.error("Error removing favorite route", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/secure/favorite-routes")
    public ResponseEntity<List<Route>> getFavoriteRoutes(Principal principal) {
        User user = this.userDetailsService.getUserByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<Route> favoriteRoutes = this.favoriteRouteService.getFavoriteRoutesByUser(user.getUserId());
        return ResponseEntity.ok(favoriteRoutes);
    }

    @PostMapping("/secure/traffic-report")
    public ResponseEntity<?> createTrafficReport(
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Principal principal
    ) {
        try {
            User user = userService.getUserByUsername(principal.getName());
            if (user == null) return ResponseEntity.status(401).body("Không tìm thấy người dùng");

            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                imageUrl = uploadResult.get("url").toString();
            }

            trafficService.reportTrafficJam(user.getUserId(), location, description, imageUrl);
            return ResponseEntity.ok("Đã gửi báo cáo");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Lỗi khi gửi báo cáo: " + e.getMessage());
        }
    }

    @PostMapping("/secure/notifications/update")
    public ResponseEntity<?> updateNotify(
            @RequestParam("routeId") int routeId,
            @RequestParam("notifyOnChanges") boolean notifyOnChanges,
            Principal principal
    ) {
        String email = principal.getName();
        User user = userDetailsService.getUserByUsername(email);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        boolean updated = notificationService.updateNotifySetting(user.getUserId(), routeId, notifyOnChanges);
        if (updated)
            return ResponseEntity.ok("Đã cập nhật thông báo.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật.");
    }

    @GetMapping("/secure/notifications")
    public ResponseEntity<?> getUserNotifications(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không xác thực");

        List<SystemNotification> notifs = systemNotificationRepository.findByUserId(user.getUserId());
        return ResponseEntity.ok(notifs);
    }


}

