package org.example.dailytier.service;

import org.example.dailytier.model.User;
import org.example.dailytier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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


    public User createUserAsAdmin(String adminUsername, String adminPassword, String newUsername, String newPassword, String newEmail) {
        User admin = userRepository.findByUsername(adminUsername);

        if (admin == null || !admin.isAdmin() || !admin.getPassword().equals(adminPassword)) {
            return null;
        }

        User newUser = new User();
        newUser.setUsername(newUsername);
        newUser.setPassword(newPassword);
        newUser.setEmail(newEmail);
        newUser.setAdmin(false);

        return userRepository.save(newUser);
    }

    public boolean deleteUserAsAdmin(String adminUsername, String adminPassword, String deleteUsername, boolean confirm) {
        User admin = userRepository.findByUsername(adminUsername);

        if (admin == null || !admin.isAdmin() || !admin.getPassword().equals(adminPassword)) {
            return false; // Unauthorized
        }

        if (!confirm) {
            System.out.println("Admin attempted to delete user without confirmation.");
            return false;
        }

        User userToDelete = userRepository.findByUsername(deleteUsername);
        if (userToDelete == null) {
            System.out.println("User not found: " + deleteUsername);
            return false;
        }

        userRepository.delete(userToDelete);
        return true;
    }

    public boolean updateUserAsAdmin(String adminUsername, String adminPassword, String updateUsername, String newEmail, String newPassword) {
        User admin = userRepository.findByUsername(adminUsername);

        if (admin == null || !admin.isAdmin() || !admin.getPassword().equals(adminPassword)) {
            return false;
        }

        User userToUpdate = userRepository.findByUsername(updateUsername);
        if (userToUpdate == null) {
            return false;
        }

        if (newEmail != null && !newEmail.isEmpty()) {
            userToUpdate.setEmail(newEmail);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            userToUpdate.setPassword(newPassword);
        }

        userRepository.save(userToUpdate);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User createUser(String username, String password, String email) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        return userRepository.save(newUser);
    }


    public boolean loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean deleteUser(Long id, String password) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                userRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public boolean updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            existingUser.setEmail(updatedUser.getEmail());
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}


