package org.example.dailytier.controller;

import org.example.dailytier.model.TierList;
import org.example.dailytier.model.User;
import org.example.dailytier.repository.TierListRepository;
import org.example.dailytier.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/tier-lists")
public class TierListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TierListRepository tierListRepository;

    // ✅ Save a new tier list
    @PostMapping
    public ResponseEntity<String> saveTierList(@RequestBody TierListRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        TierList tierList = new TierList(user, request.getTierName(), request.getItems());
        tierListRepository.save(tierList);

        return ResponseEntity.ok("Tier List Saved!");
    }

    // ✅ Get all tier lists for a user
    @GetMapping("/{username}")
    public ResponseEntity<List<TierList>> getTierLists(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<TierList> tierLists = tierListRepository.findByUser(user);
        return ResponseEntity.ok(tierLists);
    }

    // ✅ Get all tier lists from all users
    @GetMapping("/all")
    public ResponseEntity<List<TierList>> getAllTierLists(@RequestHeader("userId") Long userId) {
        // Add a debug log
        System.out.println("Fetching tier lists for userId: " + userId);

        List<TierList> allTierLists = tierListRepository.findAllExceptUser(userId);
        if (allTierLists.isEmpty()) {
            System.out.println("No tier lists found for other users.");
        }
        return ResponseEntity.ok(allTierLists);
    }


    // ✅ Request Body Class for JSON Parsing
    public static class TierListRequest {
        private String username;
        private String tierName;
        private String items; // Store as JSON string in the database

        public String getUsername() { return username; }
        public String getTierName() { return tierName; }
        public String getItems() { return items; }
    }
}
