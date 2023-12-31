package com.tkog.matchingsystem.controller;

import com.tkog.matchingsystem.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    @PostMapping("/match/add/")
    public String addPlayer(@RequestParam MultiValueMap<String, String> data) {
        System.out.println(data);
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("userId")));
        Integer rating = Integer.parseInt(Objects.requireNonNull(data.getFirst("rating")));
        Integer botId = Integer.parseInt(Objects.requireNonNull(data.getFirst("botId")));

        return matchingService.addPlayer(userId, rating, botId);
    }

    @PostMapping("/match/remove/")
    public String removePlayer(@RequestParam MultiValueMap<String, String> data) {
        System.out.println(data);
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("userId")));
        return matchingService.removePlayer(userId);
    }
}
