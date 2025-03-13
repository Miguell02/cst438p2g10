package org.example.dailytier.controller;

import org.example.dailytier.model.User;
import org.example.dailytier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/newuser")
    public User createUser(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email) {
        return userService.createUser(username, password, email);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {

        boolean isLoggedIn = userService.loginUser(username, password);
        if (isLoggedIn) {
            return "User logged in successfully!";
        } else {
            return "Invalid username or password!";
        }
    }


    @DeleteMapping("/delete/{id}") //Example test in Postman: DELETE http://localhost:8080/users/delete/1?password=testpassword
    public String deleteUser(@PathVariable Long id, @RequestParam String password) {
        boolean isDeleted = userService.deleteUser(id, password);
        return isDeleted ? "User deleted successfully" : "Password confirmation failed, user not deleted";
    }


    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return "User details updated successfully";
        } else {
            return "User update failed";
        }
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/profile")
    public User getUserProfile(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

}