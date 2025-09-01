package com.hfing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.hfing.pojo.User;
import com.hfing.services.UserService;
import com.hfing.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("token");

        try {
            // Gửi token lên Google để xác thực
            String verifyUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idTokenString;

            // Dùng HttpURLConnection hoặc RestTemplate
            URL url = new URL(verifyUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> payload = mapper.readValue(conn.getInputStream(), Map.class);

                String email = (String) payload.get("email");

                User user = userService.getUserByUsername(email);
                if (user == null) {
                    user = new User();
                    user.setEmail(email);
                    user.setFirstName((String) payload.get("given_name"));
                    user.setLastName((String) payload.get("family_name"));
                    user.setAvatarUrl(("https://res.cloudinary.com/deld9pk9w/image/upload/v1748166815/licensed-image_vrx6lu.jpg") );
                    user.setRole(User.Role.user);
                    user.setPassword("");
                    user.setActive(true);
                    user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    userService.addUserFromGoogle(user);
                }

                String jwt = JwtUtils.generateToken(email);
                return ResponseEntity.ok(Map.of("token", jwt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Google không hợp lệ");
    }

}
