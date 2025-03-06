package org.example.dailytier.controller;

import org.example.dailytier.model.Sport;
import org.example.dailytier.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller  // I had to change this to @Controller to display the html, we can create another controller using @RestController creating the REST APIs
@RequestMapping("/")
public class TierListController {

    @Autowired
    private SportRepository sportRepository;

    @GetMapping("/")
    public String home() {
        return "index.html";  // Serves signup.html from resources/static/
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login.html";  // Serves signup.html from resources/static/
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup.html";  // Serves signup.html from resources/static/
    }

    @GetMapping("/showAllSports")
    public List<Sport> showAllSports() {
        return sportRepository.findAll();
    }
}