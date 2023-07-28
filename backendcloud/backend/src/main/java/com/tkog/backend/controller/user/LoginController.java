package com.tkog.backend.controller.user;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login/")
    JSONObject getToken(@RequestParam Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        return loginService.getToken(username, password);
    }
}
