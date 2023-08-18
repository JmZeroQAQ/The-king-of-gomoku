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

        // bot下棋需要等待一秒后再来读取结果
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebSocketServer conn = WebSocketServer.users.get(userId);
        if(checkConnection(conn)) {
            conn.game.setNextChessPosition(userId, position);
        }
        return "receive bot move success";
    }
}
