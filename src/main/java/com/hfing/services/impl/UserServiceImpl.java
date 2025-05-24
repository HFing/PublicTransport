package com.hfing.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hfing.pojo.User;
import com.hfing.repositories.UserRepository;
import com.hfing.services.UserService;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("userDetailsService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), authorities);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepo.getUsers();
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        String email = params.get("email");
        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstName(params.get("firstName"));
        user.setLastName(params.get("lastName"));
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(params.get("password")));
        user.setRole(User.Role.valueOf(params.get("role")));
        user.setActive(true);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        try {
            if (avatar != null && !avatar.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
                user.setAvatarUrl(uploadResult.get("url").toString());
            }
        } catch (IOException e) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return userRepo.addUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }

    @Override
    public void deleteUserById(int id) {
        User u = userRepo.getUserById(id);
        if (u != null)
            userRepo.deleteUser(u);
    }

    @Override
    public User updateUser(User user, MultipartFile avatar) {
        User existing = userRepo.getUserById(user.getUserId());

        if (avatar == null || avatar.isEmpty()) {
            throw new IllegalArgumentException("Please select an avatar.");
        }

        if (existing == null) return null;

        existing.setEmail(user.getEmail());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setRole(user.getRole());
        existing.setActive(user.getActive());

        try {
            if (avatar != null && !avatar.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
                existing.setAvatarUrl(uploadResult.get("url").toString());
            }
        } catch (IOException e) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return userRepo.updateUser(existing);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }



}
