package org.example.dailytier.service;

import org.example.dailytier.model.User;
import org.example.dailytier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //admin
    public boolean adminLogin(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User not found: " + username);
            return false;
        }

        if (!user.isAdmin()) {
            System.out.println("User is not an admin: " + username);
            return false;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Password does not match for user: " + username);
            return false;
        }

        return true; // Login successful
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User createUser(String username, String password, String email) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);

        return userRepository.save(newUser);
    }


    public boolean loginUser(String username, String password) {

        return false;
    }


    public boolean deleteUser(Long id, String password) {

        return false;
    }


    public boolean updateUser(Long id, User updatedUser) {

        return false;
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
