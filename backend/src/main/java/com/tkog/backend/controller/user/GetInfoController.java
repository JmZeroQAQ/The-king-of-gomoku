package com.tkog.backend.controller.user;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.service.user.GetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetInfoController {
    @Autowired
    private GetInfoService getInfoService;

    @GetMapping("/user/getInfo")
    public JSONObject getInfo() {
        return getInfoService.getInfo();
    }
}
