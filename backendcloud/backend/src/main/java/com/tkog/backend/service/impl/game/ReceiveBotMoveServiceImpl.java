package com.tkog.backend.service.impl.game;

import com.tkog.backend.consumer.WebSocketServer;
import com.tkog.backend.service.game.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    private boolean checkConnection(WebSocketServer conn) {
        return conn != null;
    }

    @Override
    public String receiveBotMove(Integer userId, Integer position) {
        System.out.println("receive bot move: " + userId + " " + position);
        WebSocketServer conn = WebSocketServer.users.get(userId);
        if(checkConnection(conn)) {
            conn.game.setNextChessPosition(userId, position);
        }
        return "receive bot move success";
    }
}
