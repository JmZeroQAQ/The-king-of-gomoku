package com.tkog.botsystem;

import com.tkog.botsystem.service.impl.BotRunningServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotSystemApplication {
    public static void main(String[] args) {
        BotRunningServiceImpl.botPool.start();
        SpringApplication.run(BotSystemApplication.class, args);
    }
}