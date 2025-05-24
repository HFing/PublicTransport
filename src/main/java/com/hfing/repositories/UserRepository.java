package com.hfing.repositories;

import com.hfing.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository {
    User getUserByUsername(String username);
    User addUser(User u);
    List<User> getUsers();
    User getUserById(int id);
    void deleteUser(User user);
    User updateUser(User user);
    boolean existsByEmail(String email);
    boolean authenticate(String username, String password);

}
