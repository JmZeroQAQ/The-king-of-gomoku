package com.tkog.backend.service.user;

import com.alibaba.fastjson2.JSONObject;

public interface LoginService {
    JSONObject getToken(String username, String password);
}
