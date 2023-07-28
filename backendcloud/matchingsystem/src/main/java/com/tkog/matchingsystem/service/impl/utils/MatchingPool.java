package com.tkog.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread {
    private static List<Player> players = new ArrayList<>();

    // 只有一个线程一个对象
    private final ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private final static String startGameUrl = "http://127.0.0.1:3000/game/start/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player>  newPlayers = new ArrayList<>();
            for (Player player: players) {
                if(!player.getUserId().equals(userId)) {
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }

    // 将当前所有玩家的等待时间+1
    private void increaseWaitingTime() {
        for (Player player: players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean checkMatched(Player aPlayer, Player bPlayer) {
        int ratingDelta = Math.abs(aPlayer.getRating() - bPlayer.getRating());
        int waitingTime = Math.min(aPlayer.getWaitingTime(), bPlayer.getWaitingTime());
        return ratingDelta <= waitingTime * 10;
    }

    // 匹配成功
    private void matchFound(Integer aUserId, Integer bUserId) {
        // 匹配成功
        System.out.println("matchFound");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.set("a_id", aUserId.toString());
        data.set("b_id", bUserId.toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    private void matchPlayers() {
        System.out.println(players.toString());
        boolean[] used = new boolean[players.size()];
        for(int i = 0; i < players.size(); i++) {
            if(used[i]) continue;
            for (int j = i + 1; j < players.size(); j++) {
                if(used[j]) continue;
                Player aPlayer = players.get(i), bPlayer = players.get(j);
                if(checkMatched(aPlayer, bPlayer)) {
                    used[i] = used[j] = true;
                    matchFound(aPlayer.getUserId(), bPlayer.getUserId());
                    break;
                }
            }
        }

        List<Player> newPlayers = new ArrayList<>();
        for(int i = 0; i < players.size(); i++) {
            if(!used[i]) newPlayers.add(players.get(i));
        }

        players = newPlayers;
    }
    @Override
    public void run() {
        // 每秒钟匹配一次
        while(true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
