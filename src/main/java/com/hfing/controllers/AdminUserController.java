package com.hfing.controllers;

import com.cloudinary.Cloudinary;
import com.hfing.pojo.User;
import com.hfing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
public class AdminUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/login")
    public String loginView() {
        return "admin/login";
    }
    @GetMapping("/admin")
    public String dashboardView() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/user")
    public String userView(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String createUserForm(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }


    @PostMapping("/admin/user/create")
    public String createUser(@RequestParam Map<String, String> params,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             Model model) {
        try {
            userService.addUser(params, avatarFile);
            return "redirect:/admin/user";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating user: " + e.getMessage());
            return "admin/user/create";
        }
    }

    @GetMapping("/admin/user/{id}")
    public String userDetail(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String confirmDelete(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/user";
    }


    @GetMapping("/admin/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             Model model) {
        try {
            userService.updateUser(user, avatarFile);
            return "redirect:/admin/user";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating user: " + e.getMessage());
            model.addAttribute("user", user);
            return "admin/user/update";
        }
    }



}
