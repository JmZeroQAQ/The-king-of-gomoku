package com.tkog.backend.controller.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RankListController {

    @Autowired
    RankListService rankListService;

    @GetMapping("/api/rankList/getList/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        return rankListService.getList(data);
    }
}
