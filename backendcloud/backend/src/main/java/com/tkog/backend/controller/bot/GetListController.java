package com.tkog.backend.controller.bot;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.service.bot.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetListController {
    @Autowired
    private GetListService getListService;

    @GetMapping("/api/bot/getList/")
    public JSONObject getList() {
        return getListService.getList();
    }
}
