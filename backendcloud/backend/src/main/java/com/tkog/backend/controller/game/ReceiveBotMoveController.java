package com.tkog.backend.controller.game;

import com.tkog.backend.service.game.ReceiveBotMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ReceiveBotMoveController {
    @Autowired
    ReceiveBotMoveService receiveBotMoveService;

    @PostMapping("/game/bot/move/")
    public String receiveBotMove(@RequestParam MultiValueMap<String, String> data) {
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("userId")));
        Integer position = Integer.parseInt(Objects.requireNonNull(data.getFirst("position")));
        return receiveBotMoveService.receiveBotMove(userId, position);
    }
}
