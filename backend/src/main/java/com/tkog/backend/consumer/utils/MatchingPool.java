package com.tkog.backend.consumer.utils;


import com.tkog.backend.consumer.WebSocketServer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread {
    private static List<Player> players = new ArrayList<>();

    // 只有一个线程一个对象
    private final ReentrantLock lock = new ReentrantLock();

    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            players.add(new Player(userId, rating));
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

    // 匹配成功
    private void matchFound(Integer aUserId, Integer bUserId) {
        // 匹配成功
        WebSocketServer.matchFound(aUserId, bUserId);
    }

    private boolean checkMatched(Player aPlayer, Player bPlayer) {
        return true;
    }

    private void matchPlayers() {
        System.out.println(players);
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
