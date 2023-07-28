package com.tkog.matchingsystem.service.impl;

import com.tkog.matchingsystem.service.MatchingService;
import com.tkog.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {

    public final static MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating) {
        System.out.println("addPlayer");
        matchingPool.addPlayer(userId, rating);
        return "add Player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("removePlayer");
        matchingPool.removePlayer(userId);
        return "remove player success";
    }
}
