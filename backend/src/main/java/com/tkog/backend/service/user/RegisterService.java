package com.tkog.backend.service.user;

import com.alibaba.fastjson2.JSONObject;

public interface RegisterService {
    JSONObject register(String username, String password, String confirmedPassword);
}
