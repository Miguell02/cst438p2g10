package org.example.dailytier.controller;

import org.example.dailytier.model.User;
import org.example.dailytier.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        boolean isLoggedIn = userService.adminLogin(username, password);
        if (isLoggedIn) {
            return "Admin logged in successfully!";
        } else {
            return "Invalid admin credentials!";
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam String username, @RequestParam String password) {
        boolean isAdmin = userService.adminLogin(username, password);
        if (isAdmin) {
            return userService.getAllUsers();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized access.");
        }
    }

//    @PutMapping("/users")
//    public ResponseEntity<?> createUser(@RequestParam String username, @RequestParam String password,
//                                        @RequestParam String newUsername, @RequestParam String newPassword,
//                                        @RequestParam String newEmail) {
//        User createdUser = userService.createUserAsAdmin(username, password, newUsername, newPassword, newEmail);
//
//        if (createdUser == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action: Only admins can create users.");
//        }
//
//        return ResponseEntity.ok("User created successfully: " + createdUser.getUsername());
//    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam String username, @RequestParam String password,
                                             @PathVariable Long id, @RequestParam boolean confirm) {
        boolean isAdmin = userService.adminLogin(username, password);
        if (!isAdmin || !confirm) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action.");
        }
        boolean isDeleted = userService.deleteUser(id, null);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found or deletion failed.");
        }
    }


    @PatchMapping("/users")
    public ResponseEntity<String> updateUser(@RequestParam String username, @RequestParam String password, @RequestParam String updateUsername,
                                             @RequestParam(required = false) String newEmail, @RequestParam(required = false) String newPassword) {
        boolean isUpdated = userService.updateUserAsAdmin(username, password, updateUsername, newEmail, newPassword);

        if (isUpdated) {
            return ResponseEntity.ok("User " + updateUsername + " updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action or user not found.");
        }
    }

    @PutMapping("/users")
    public ResponseEntity<?> createUserAsAdmin(@RequestParam String username,
                                               @RequestParam String password,
                                               @RequestBody User newUser) {
        User createdUser = userService.createUserAsAdmin(username, password, newUser.getUsername(), newUser.getPassword(), newUser.getEmail());

        if (createdUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action: Only admins can create users.");
        }

        return ResponseEntity.ok("User created successfully: " + createdUser.getUsername());
    }





}
