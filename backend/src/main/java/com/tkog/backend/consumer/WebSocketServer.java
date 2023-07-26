package com.tkog.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.consumer.utils.JwtAuthentication;
import com.tkog.backend.consumer.utils.MatchingPool;
import com.tkog.backend.mapper.UserMapper;
import com.tkog.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {

    // 线程安全的Map
    // userId 对应 webSocket 连接
    public final static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();

    private User user;
    private Session session;

    private static UserMapper userMapper;

    public final static MatchingPool matchingPool = new MatchingPool();

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        System.out.println("connected");
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(this.user != null) {
            users.put(userId, this);
        } else {
            this.session.close();
        }

        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected");
        if(this.user != null) {
            users.remove(this.user.getId());
        }
    }

    private void startMatching() {
        System.out.println("start matching");
        matchingPool.addPlayer(this.user.getId(), this.user.getRating());

    }

    private void stopMatching() {
        System.out.println("stop matching");
        matchingPool.removePlayer(this.user.getId());
    }

    private static JSONObject getOpponentInfo(Integer userId) {
        JSONObject res = new JSONObject();
        User user = userMapper.selectById(userId);
        res.put("event", "match-found");
        res.put("name", user.getUsername());
        res.put("avatar", user.getAvatar());
        res.put("rating", user.getRating());

        return res;
    }

    // 发送对手信息
    public static void matchFound(Integer aUserId, Integer bUserId) {
        JSONObject resToA = getOpponentInfo(bUserId);
        JSONObject resToB = getOpponentInfo(aUserId);

        users.get(aUserId).sendMessage(resToA.toJSONString());
        users.get(bUserId).sendMessage(resToB.toJSONString());
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("receive message");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)) {
            startMatching();
        } else if("stop-matching".equals(event)) {
            stopMatching();
        } else if("move".equals(event)) {

        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
