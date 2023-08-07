package com.tkog.backend.service.impl.bot;

import com.tkog.backend.mapper.BotMapper;
import com.tkog.backend.pojo.Bot;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.bot.RemoveService;
import com.tkog.backend.utils.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        User user = GetUser.getUser();

        Integer botId = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(botId);
        Map<String, String> resp = new HashMap<>();

        if(bot == null) {
            resp.put("message", "Bot不存在或已删除");
            return resp;
        }

        if(!bot.getUserId().equals(user.getId())) {
            resp.put("message", "没有权限删除该Bot");
            return resp;
        }

        botMapper.deleteById(bot);

        resp.put("message", "success");

        return resp;
    }
}
