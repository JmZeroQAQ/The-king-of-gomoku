package com.tkog.backend.service.impl.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkog.backend.mapper.BotMapper;
import com.tkog.backend.pojo.Bot;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.bot.AddService;
import com.tkog.backend.utils.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        User user = GetUser.getUser();

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

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        if(botMapper.selectCount(queryWrapper) >= 10) {
            resp.put("message", "每个用户最多只能创建10个bot");
            return resp;
        }

        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, now, now);

        botMapper.insert(bot);
        resp.put("message", "success");

        return resp;
    }
}
