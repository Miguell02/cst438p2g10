package org.example.dailytier.controller;

import org.example.dailytier.model.User;
import org.example.dailytier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/// START WORKING ON BYCRYPT BRAH
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // create new perosn with the json
    @PostMapping("/newuser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user.getUsername(), user.getPassword(), user.getEmail());
        return ResponseEntity.ok(createdUser);
    }

    // log in existing broski
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean isLoggedIn = userService.loginUser(username, password);
        if (isLoggedIn) {
            return ResponseEntity.ok("Okay you logged in brah!");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");

        }
    }

    @GetMapping("/userId")
    public ResponseEntity<Long> getUserIdByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user.getId()); // Assuming getId() returns the user's ID
        } else {
            return ResponseEntity.status(404).body(null); // User not found
        }
    }


    @DeleteMapping("/delete/{id}") // Example test in Postman: DELETE http://localhost:8080/users/delete/1?password=testpassword
    public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestParam String password) {
        System.out.println("Deleting user: " + id);
        boolean isDeleted = userService.deleteUser(id, password);

        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(400).body("Password confirmation failed or user not found");
        }
    }


    // update details brah
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.ok("User details updated successfully");
        } else {
            return ResponseEntity.status(400).body("User update failed");
        }
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // all user brah:
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile")
    public User getUserProfile(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

}