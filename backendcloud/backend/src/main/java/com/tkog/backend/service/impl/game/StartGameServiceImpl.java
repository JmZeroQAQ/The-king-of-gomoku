package com.tkog.backend.service.impl.game;

import com.tkog.backend.consumer.WebSocketServer;
import com.tkog.backend.service.game.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("start game" + aId + " " + bId);
        WebSocketServer.matchFound(aId, bId);
        return "start game success";
    }
}
