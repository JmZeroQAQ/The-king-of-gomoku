package com.tkog.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.consumer.WebSocketServer;
import com.tkog.backend.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    private final Integer rows;
    private final Integer cols;
    // 选手每回合时间
    private final Integer waitTime;

    private Integer step = 0;
    // 游戏地图,0表示没有棋子,1表示白子,2表示黑子
    private final int[][] gameMap;

    // a是黑子，先手下棋
    private final Integer aUserId;
    // b是白子,后手下棋
    private final Integer bUserId;

    private Integer loserUserId = -1;

    private final List<Integer> winChessPieces = new ArrayList<>();

    private Integer aNextChessPosition = -1;
    private Integer bNextChessPosition = -1;

    public final ReentrantLock lock = new ReentrantLock();

    public Game(Integer rows, Integer cols, Integer waitTime, Integer aUserId, Integer bUserId) {
        this.rows = rows;
        this.cols = cols;
        this.aUserId = aUserId;
        this.bUserId = bUserId;
        this.waitTime = waitTime;

        this.gameMap = new int[rows][cols];
    }

    public void createMap() {
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                this.gameMap[i][j] = 0;
    }

    private void setGameMap(Integer position, Integer value) {
        int x = position / this.cols;
        int y = position % this.cols;

        this.gameMap[x][y] = value;
    }

    // 不同的用户选择不同的设置下一个棋子位置的函数
    public void setNextChessPosition(Integer userId, Integer position) {
        // 黑子需要当前回合是奇数,且同个回合内的上一次操作已经被处理完才可落子
        if(this.aUserId.equals(userId) && aNextChessPosition == -1 && this.step % 2 == 0) {
            setANextChessPosition(position);
        } else if(this.bUserId.equals(userId) && bNextChessPosition == -1 && this.step % 2 == 1) {
            setBNextChessPosition(position);
        }
    }

    // 我们只用这个函数修改aNextChessPosition,所以是线程安全的
    private void setANextChessPosition(Integer value) {
        lock.lock();
        try {
            this.aNextChessPosition = value;
        } finally {
            lock.unlock();
        }
    }

    private void setBNextChessPosition(Integer value) {
        lock.lock();
        try {
            this.bNextChessPosition = value;
        } finally {
            lock.unlock();
        }
    }

    // true表示游戏结束
    private boolean isGameOver() {
        return this.loserUserId != -1;
    }

    // 判断落子的合法性
    private boolean checkValid(Integer position) {
        if(position < 0 || position >= this.rows * this.cols) return false;

        int x = position / this.cols;
        int y = position % this.cols;

        return this.gameMap[x][y] == 0;
    }

    private void getAChessPiece() {
        for(int i = 0; i < this.waitTime * 10; i++) {
            try {
                Thread.sleep(100);
                if(aNextChessPosition != -1) {
                    // 判断下一个位置的合法性
                    if(checkValid(aNextChessPosition)) {
                        // value: 1 表示白子, 2 表示黑子
                        // a是黑子玩家,所以使用黑子
                        setGameMap(aNextChessPosition, 2);
                        sendMoveMessage(aNextChessPosition);
                        setANextChessPosition(-1);
                        return;
                    }
                    setANextChessPosition(-1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 走到这里说明玩家在规定时间内没有响应
        this.loserUserId = this.aUserId;
    }

    private void getBChessPiece() {
        for(int i = 0; i < this.waitTime * 10; i++) {
            try {
                Thread.sleep(100);
                if(bNextChessPosition != -1) {
                    // 判断下一个位置的合法性
                    if(checkValid(bNextChessPosition)) {
                        // value: 1 表示白子, 2 表示黑子
                        // b是白子玩家,所以使用白子
                        setGameMap(bNextChessPosition, 1);
                        // 广播新的位置信息
                        sendMoveMessage(bNextChessPosition);
                        setBNextChessPosition(-1);
                        return;
                    }
                    setBNextChessPosition(-1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 走到这里说明玩家在规定时间内没有响应
        this.loserUserId = this.bUserId;
    }

    // 检查局面
    private void checkSituation() {
        boolean[][] used = new boolean[this.rows][this.cols];
        Integer loserId = -1;

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                if(this.gameMap[i][j] != 0) {
                    final int dx = 1, dy = 1;
                    final int currentValue = this.gameMap[i][j];
                    List<Integer> dResult = new ArrayList<>();

                    // 判断水平方向
                    for(int k = 0; k < 4; k++) {
                        final int ny = j + dy * (k + 1);
                        if(ny >= this.cols || this.gameMap[i][ny] != currentValue) break;
                        if(k == 3) dResult.add(0);
                    }

                    // 判断竖直方向
                    for(int k = 0; k < 4; k++) {
                        final int nx = i + dx * (k + 1);
                        if(nx >= this.rows || this.gameMap[nx][j] != currentValue) break;
                        if(k == 3) dResult.add(1);
                    }

                    // 判断左下方向
                    for(int k = 0; k < 4; k++) {
                        final int nx = i + dx * (k + 1);
                        final int ny = j - dy * (k + 1);
                        if(nx >= this.rows || ny < 0 ||  this.gameMap[nx][ny] != currentValue) break;
                        if(k == 3) dResult.add(2);
                    }

                    // 判断右下方向
                    for(int k = 0; k < 4; k++) {
                        final int nx = i + dx * (k + 1);
                        final int ny = j + dy * (k + 1);
                        if(nx >= this.rows || ny >= this.cols || this.gameMap[nx][ny] != currentValue) break;
                        if(k == 3) dResult.add(3);
                    }

                    if(dResult.size() > 0) {
                        if(!used[i][j]) used[i][j] = true;
                        if(this.gameMap[i][j] == 1) loserId = this.aUserId;
                        else loserId = this.bUserId;

                        for(int d: dResult) {
                            if(d == 0) {
                                for(int k = 0; k < 4; k++){
                                    final int ny = j + dy * (k + 1);
                                    if(!used[i][ny]) used[i][ny] = true;
                                }
                            } else if(d == 1) {
                                for(int k = 0; k < 4; k++){
                                    final int nx = i + dx * (k + 1);
                                    if(!used[nx][j]) used[nx][j] = true;
                                }
                            } else if(d == 2) {
                                for(int k = 0; k < 4; k++) {
                                    final int nx = i + dx * (k + 1);
                                    final int ny = j - dy * (k + 1);
                                    if(!used[nx][ny]) used[nx][ny] = true;
                                }
                            } else if(d == 3) {
                                for(int k = 0; k < 4; k++) {
                                    final int nx = i + dx * (k + 1);
                                    final int ny = j + dy * (k + 1);
                                    if(!used[nx][ny]) used[nx][ny] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if(loserId != -1) {
            this.loserUserId = loserId;
            for(int i = 0; i < this.rows; i++)
                for(int j = 0; j < this.cols; j++)
                    if(used[i][j]) this.winChessPieces.add(this.cols * i + j);
        }
    }


    private void sendAllMessage(String msg) {
        WebSocketServer aWebSocketServer = WebSocketServer.users.get(aUserId);
        WebSocketServer bWebSocketServer = WebSocketServer.users.get(bUserId);
        if(aWebSocketServer != null && aWebSocketServer.game != null) {
            aWebSocketServer.sendMessage(msg);
        }
        if(bWebSocketServer != null && bWebSocketServer.game != null) {
            bWebSocketServer.sendMessage(msg);
        }
    }

    private void sendMoveMessage(int position) {
        JSONObject resp = new JSONObject();
        resp.put("event", "move");
        resp.put("newPosition", position);

        sendAllMessage(resp.toJSONString());
    }

    private void sendResult() {
        JSONObject resp = new JSONObject();
        User loser = WebSocketServer.userMapper.selectById(loserUserId);

        resp.put("event", "result");
        resp.put("name", loser.getUsername());
        resp.put("winSet", this.winChessPieces.toArray());

        sendAllMessage(resp.toJSONString());
    }

    private void closeGame() {
        WebSocketServer aWebSocketServer = WebSocketServer.users.get(aUserId);
        WebSocketServer bWebSocketServer = WebSocketServer.users.get(bUserId);
        if(aWebSocketServer != null) {
            aWebSocketServer.game = null;
        }
        if(bWebSocketServer != null) {
            bWebSocketServer.game = null;
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < this.rows * this.cols; i++) {
            // i为回合数, 偶数为黑子回合,奇数为白子回合
            if(i % 2 == 0) {
                getAChessPiece();
            } else {
                getBChessPiece();
            }
            checkSituation();
            // 判断游戏是否结束
            if(isGameOver()) {
                sendResult();
                // 关闭游戏
                closeGame();
                break;
            }

            // 回合数+1
            this.step++;
        }
    }
}
