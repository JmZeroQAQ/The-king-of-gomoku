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

    @GetMapping("/record/getAllList/")
    public JSONObject getRecordAll(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getRecordListService.getAllRecord(page);
    }

    @GetMapping("/record/getInfo/")
    public JSONObject getRecordInfo(@RequestParam Map<String, String> data) {
        Integer recordId = Integer.parseInt(data.get("record_id"));

        return getRecordListService.getRecordInfo(recordId);
    }
}
