package org.example.dailytier.controller;

import org.example.dailytier.model.User;
import org.example.dailytier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        boolean isLoggedIn = userService.loginUser(username, password);
        if (isLoggedIn) {
            return ResponseEntity.ok("User logged in successfully!");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password you stupid fuck!");
        }
    }


    @DeleteMapping("/delete/{id}") //Example test in Postman: DELETE http://localhost:8080/users/delete/1?password=testpassword
    public String deleteUser(@PathVariable Long id, @RequestParam String password) {
        boolean isDeleted = userService.deleteUser(id, password);
        return isDeleted ? "User deleted successfully" : "Password confirmation failed, user not deleted";
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