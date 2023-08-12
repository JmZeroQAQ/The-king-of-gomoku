package com.tkog.backend.service.impl.bot;

import com.tkog.backend.mapper.BotMapper;
import com.tkog.backend.pojo.Bot;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.bot.UpdateService;
import com.tkog.backend.utils.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        User user = GetUser.getUser();

        Integer botId = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> resp = new HashMap<>();

        if(title == null || title.length() == 0) {
            resp.put("message", "标题不能为空");
            return resp;
        }

        if(title.length() > 100) {
            resp.put("message", "标题长度不能大于100");
            return resp;
        }

        if(description == null || description.length() == 0) {
            description = "这个用户很懒，什么也没留下~";
        }

        if(description.length() > 300) {
            resp.put("message", "Bot描述不能大于300");
            return resp;
        }

        if(content == null || content.length() == 0) {
            resp.put("message", "Bot代码不能为空");
            return resp;
        }

        if(content.length() > 10000) {
            resp.put("message", "Bot代码长度不能超过10000");
            return resp;
        }

        Bot bot = botMapper.selectById(botId);
        if(bot == null) {
            resp.put("message", "Bot不存在或已被删除");
            return resp;
        }

        if(!bot.getUserId().equals(user.getId())) {
            resp.put("message", "没有权限修改该Bot");
            return resp;
        }

        Bot newBot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getCreateTime(),
                new Date()
        );

        botMapper.updateById(newBot);

        resp.put("message", "success");

        return resp;
    }
}
