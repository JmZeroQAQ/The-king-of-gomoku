package com.tkog.backend.service.impl.user;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.user.LoginService;
import com.tkog.backend.utils.GetUser;
import com.tkog.backend.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public JSONObject getToken(String username, String password) {
        User user = GetUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());

        JSONObject resp = new JSONObject();
        resp.put("message", "success");
        resp.put("token", jwt);

        return resp;
    }
}
