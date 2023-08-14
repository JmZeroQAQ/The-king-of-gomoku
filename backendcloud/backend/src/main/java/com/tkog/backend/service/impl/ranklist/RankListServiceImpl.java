package com.tkog.backend.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkog.backend.enums.AppHttpCodeEnum;
import com.tkog.backend.mapper.RecordMapper;
import com.tkog.backend.mapper.UserMapper;
import com.tkog.backend.pojo.Record;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RankListServiceImpl implements RankListService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public JSONObject getList(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        if(!data.containsKey("page")) {
            resp.put("code", AppHttpCodeEnum.SYSTEM_ERROR.getCode());
            resp.put("message", AppHttpCodeEnum.SYSTEM_ERROR.getMessage());
            return resp;
        }

        int page = Integer.parseInt(data.get("page"));

        IPage<User> userIPage = new Page<>(page, 15);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        List<User> users = userMapper.selectPage(userIPage, queryWrapper).getRecords();

        List<JSONObject> items = new ArrayList<>();
        for(User user: users) {
            JSONObject item = new JSONObject();
            Integer userId = user.getId();

            item.put("id", userId);
            item.put("name", user.getUsername());
            item.put("rating", user.getRating());

            QueryWrapper<Record> queryWrapper1 = new QueryWrapper<>();
            // 查询用户的总场次
            queryWrapper1.eq("a_id", userId).or().eq("b_id", userId);
            Long counts = recordMapper.selectCount(queryWrapper1);

            QueryWrapper<Record> queryWrapper2 = new QueryWrapper<>();
            // 查询用户赢的场次
            queryWrapper2.eq("winner_id", userId);
            Long winCounts = recordMapper.selectCount(queryWrapper2);

            item.put("counts", counts);
            item.put("win_counts", winCounts);

            items.add(item);
        }

        resp.put("message", "success");
        resp.put("rank_list", items);

        return resp;
    }
}
