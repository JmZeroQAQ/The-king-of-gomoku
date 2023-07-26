package com.tkog.backend;

import com.tkog.backend.consumer.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        WebSocketServer.matchingPool.start();
        SpringApplication.run(BackendApplication.class, args);
    }

}
