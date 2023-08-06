package com.tkog.matchingsystem.service;

public interface MatchingService {
    public String addPlayer(Integer userId, Integer rating, Integer botId);
    public String removePlayer(Integer userId);
}
