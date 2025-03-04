package org.example.dailytier.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TierListController {

    @GetMapping("/demo")
    public String home() {
        return "Hello World";
    }



}

// sql request query // start