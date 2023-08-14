package com.tkog.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkog.backend.enums.AppHttpCodeEnum;
import com.tkog.backend.mapper.RecordMapper;
import com.tkog.backend.mapper.UserMapper;
import com.tkog.backend.pojo.Record;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.record.GetRecordListService;
import com.tkog.backend.utils.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    private List<JSONObject> getRecords(List<Record> records) {
        List<JSONObject> items = new LinkedList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(Record record: records) {
            User userA = userMapper.selectById(record.getAId());
            User userB = userMapper.selectById(record.getBId());
            JSONObject item = new JSONObject();
            item.put("aName", userA.getUsername());
            item.put("bName", userB.getUsername());
            Date createTime = record.getCreateTime();
            item.put("create_time", simpleDateFormat.format(createTime));
            item.put("record_id", record.getId());

            if(record.getWinnerId().equals(userA.getId())) item.put("winnerName", userA.getUsername());
            else item.put("winnerName", userB.getUsername());

            items.add(item);
        }

        return items;
    }

    private int getPage(Map<String, String> data) {
        if(!data.containsKey("page")) return -1;
        return Integer.parseInt(data.get("page"));
    }

    @Override
    public JSONObject getAllRecord(Map<String, String> data) {
        JSONObject resp = new JSONObject();

        int page = getPage(data);
        // 没有传入页数，直接返回
        if(page == -1) {
            resp.put("code", AppHttpCodeEnum.SYSTEM_ERROR.getCode());
            resp.put("message", AppHttpCodeEnum.SYSTEM_ERROR.getMessage());
            return resp;
        }

        // 每页15条记录
        IPage<Record> recordIPage = new Page<>(page, 15);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Record> records = recordMapper.selectPage(recordIPage, queryWrapper).getRecords();

        resp.put("records", getRecords(records));
        resp.put("message", "success");

        return resp;
    }

    @Override
    public JSONObject getRecordInfo(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        if(!data.containsKey("record_id")) {
            resp.put("message", "未传入对局Id");
            return resp;
        }

        Integer recordId = Integer.valueOf(data.get("record_id"));

        Record record = recordMapper.selectById(recordId);
        if(record == null) {
            resp.put("message", "记录不存在");
            return resp;
        }

        User userA = userMapper.selectById(record.getAId());
        User userB = userMapper.selectById(record.getBId());
        resp.put("aName", userA.getUsername());
        resp.put("bName", userB.getUsername());
        resp.put("historySteps", record.getHistorySteps());
        resp.put("win_set", record.getWinSet());

        if(record.getWinnerId().equals(userA.getId())) resp.put("winnerName", userA.getUsername());
        else resp.put("winnerName", userB.getUsername());

        resp.put("message", "success");

        return resp;
    }

    @Override
    public JSONObject getMyRecords(Map<String, String> data) {
        User user = GetUser.getUser();

        JSONObject resp = new JSONObject();

        int page = getPage(data);
        // 没有传入页数，直接返回
        if(page == -1) {
            resp.put("code", AppHttpCodeEnum.SYSTEM_ERROR.getCode());
            resp.put("message", AppHttpCodeEnum.SYSTEM_ERROR.getMessage());
            return resp;
        }

        IPage<Record> recordIPage = new Page<>(page, 15);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").eq("a_id", user.getId()).or().eq("b_id", user.getId());
        List<Record> records = recordMapper.selectPage(recordIPage, queryWrapper).getRecords();

        resp.put("records", getRecords(records));
        resp.put("message", "success");

        return resp;
    }
}
