//package org.example.dailytier.controller;
//
//import org.example.dailytier.model.TierList;
//import org.example.dailytier.model.TierListSport;
//import org.example.dailytier.model.User;
//import org.example.dailytier.model.TierRankRequest;
//import org.example.dailytier.repository.TierListRepository;
//import org.example.dailytier.repository.TierListSportRepository;
//import org.example.dailytier.repository.SportRepository;
//import org.example.dailytier.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/tier-lists")
//public class TierListApiController {
//
//    @Autowired
//    private TierListRepository tierListRepository;
//
//    @Autowired
//    private TierListSportRepository tierListSportRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private SportRepository sportRepository;
//
//    @PostMapping
//    public ResponseEntity<String> createTierList(@RequestBody TierRankRequest request) {
//        User user = userRepository.findByUsername(request.getUsername());
//        if (user == null) {
//            return ResponseEntity.badRequest().body("User not found.");
//        }
//
//        TierList tierList = new TierList();
//        tierList.setUser(user);
//        tierList.setTierName(request.getTierName());
//        tierListRepository.save(tierList);
//
//        List<TierListSport> tierListSports = new ArrayList<>();
//        for (TierRankRequest.SportRank rank : request.getSports()) {
//            TierListSport tls = new TierListSport();
//            tls.setTierList(tierList);
//            tls.setSport(sportRepository.findById(rank.getSportId()).orElse(null));
//            tls.setTierRank(rank.getTierRank());
//            tierListSports.add(tls);
//        }
//
//        tierListSportRepository.saveAll(tierListSports);
//        return ResponseEntity.ok("Tier list created successfully!");
//    }
//
//    @GetMapping
//    public List<TierList> getAllTierLists() {
//        return tierListRepository.findAll();
//    }
//
//}
