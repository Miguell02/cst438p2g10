package org.example.dailytier.controller;

import org.example.dailytier.model.User;
import org.example.dailytier.service.UserService;
import org.springframework.http.HttpStatus;
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
}
