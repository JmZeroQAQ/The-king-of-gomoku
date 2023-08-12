package com.tkog.backend.service.impl.bot;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkog.backend.mapper.BotMapper;
import com.tkog.backend.pojo.Bot;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.bot.GetListService;
import com.tkog.backend.utils.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListServiceImpl implements GetListService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public JSONObject getList() {
        User user = GetUser.getUser();

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());

        JSONObject resp = new JSONObject();
        resp.put("message", "success");
        List<Bot> bots = botMapper.selectList(queryWrapper);
        resp.put("bots", bots);

        return resp;
    }
}
