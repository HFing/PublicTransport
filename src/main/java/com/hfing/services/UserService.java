package com.hfing.services;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.hfing.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    User addUser(Map<String, String> params, MultipartFile avatar);
    List<User> getUsers();
    User getUserById(int id);
    void deleteUserById(int id);
    User updateUser(User user, MultipartFile avatar);
    boolean authenticate(String username, String password);

}
