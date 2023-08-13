package com.tkog.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkog.backend.mapper.RecordMapper;
import com.tkog.backend.mapper.UserMapper;
import com.tkog.backend.pojo.Record;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getAllRecord(Integer page) {
        // 每页15条记录
        IPage<Record> recordIPage = new Page<>(page, 15);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Record> records = recordMapper.selectPage(recordIPage, queryWrapper).getRecords();

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

        JSONObject resp = new JSONObject();
        resp.put("records", items);
        resp.put("message", "success");

        return resp;
    }

    @Override
    public JSONObject getRecordInfo(Integer recordId) {
        Record record = recordMapper.selectById(recordId);
        JSONObject resp = new JSONObject();
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
}
