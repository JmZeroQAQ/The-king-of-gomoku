package com.tkog.botsystem.service.impl;

import com.tkog.botsystem.service.BotRunningService;
import com.tkog.botsystem.service.impl.utils.BotPool;
import org.springframework.stereotype.Service;

@Service
public class BotRunningServiceImpl implements BotRunningService {
    public final static BotPool botPool = new BotPool();
    @Override
    public String addBot(Integer userId, String botCode, String input, Integer color) {
        botPool.addBot(userId, botCode, input, color);
        return "add bot success";
    }
}
