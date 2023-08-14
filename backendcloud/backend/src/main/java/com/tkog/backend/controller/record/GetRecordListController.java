package com.tkog.backend.controller.record;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRecordListController {
    @Autowired
    GetRecordListService getRecordListService;

    @GetMapping("/api/record/getAllList/")
    public JSONObject getRecordAll(@RequestParam Map<String, String> data) {
        return getRecordListService.getAllRecord(data);
    }

    @GetMapping("/api/record/getInfo/")
    public JSONObject getRecordInfo(@RequestParam Map<String, String> data) {
        return getRecordListService.getRecordInfo(data);
    }

    @GetMapping("/api/record/getMyRecords/")
    public JSONObject getMyRecords(@RequestParam Map<String, String> data) {
        return getRecordListService.getMyRecords(data);
    }
}
