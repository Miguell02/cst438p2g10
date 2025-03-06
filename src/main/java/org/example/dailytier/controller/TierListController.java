package org.example.dailytier.controller;

import org.example.dailytier.model.Sport;
import org.example.dailytier.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TierListController {

    @Autowired
    private SportRepository sportRepository;

    @GetMapping("/demo")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/showAllSports")
    public List<Sport> showAllSports() {
        return sportRepository.findAll();
    }
}