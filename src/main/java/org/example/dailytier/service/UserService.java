package org.example.dailytier.service;

import org.example.dailytier.model.TierList;
import org.example.dailytier.model.User;
import org.example.dailytier.repository.TierListRepository;
import org.example.dailytier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TierListRepository tierListRepository;

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private boolean isAdminValid(String adminUsername, String adminPassword) {
        User admin = userRepository.findByUsername(adminUsername);
        return admin != null && admin.isAdmin() && passwordEncoder.matches(adminPassword, admin.getPassword());
    }

    public boolean adminLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.isAdmin() || !passwordEncoder.matches(password, user.getPassword())) {
            logger.warning("Login failed for admin user: " + username);
            return false;
        }
        return true;
    }

    public User createUserAsAdmin(String adminUsername, String adminPassword, String newUsername, String newPassword, String newEmail) {
        User admin = userRepository.findByUsername(adminUsername);

        if (admin == null || !admin.isAdmin() || !passwordEncoder.matches(adminPassword, admin.getPassword())) {
            System.out.println("ADMIN CHECK FAILED");
            return null;
        }

        if (userRepository.findByUsername(newUsername) != null) {
            System.out.println("DUPLICATE USER");
            return null;
        }

        User newUser = new User();
        newUser.setUsername(newUsername);
        newUser.setPassword(passwordEncoder.encode(newPassword));
        newUser.setEmail(newEmail);

        return userRepository.save(newUser);
    }

    public boolean deleteUserAsAdmin(String adminUsername, String adminPassword, String deleteUsername, boolean confirm) {
        User admin = userRepository.findByUsername(adminUsername);

        if (admin == null || !admin.isAdmin() || !passwordEncoder.matches(adminPassword, admin.getPassword())) {
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

        if (admin == null || !admin.isAdmin() || !passwordEncoder.matches(adminPassword, admin.getPassword())) {
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
            userToUpdate.setPassword(passwordEncoder.encode(newPassword));
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

    public List<TierList> getTierListsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return tierListRepository.findByUser(user);
        } else {
            return new ArrayList<>();
        }
    }

    public List<TierList> getAllTierLists() {
        return tierListRepository.findAll();
    }

    public boolean deleteUser(Long userId, String password) {
        System.out.println("Deleting user: " + userId);
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            System.out.println("Stored password (hashed): " + user.getPassword());
            System.out.println("Password provided (plain text): " + password);

            if (password != null) {
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    return false;
                }
            }

            userRepository.delete(user);
            return true;
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

    public User updateUserDetails(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
                user.setPassword(hashedPassword);
            }

            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());

            return userRepository.save(user);
        }
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
