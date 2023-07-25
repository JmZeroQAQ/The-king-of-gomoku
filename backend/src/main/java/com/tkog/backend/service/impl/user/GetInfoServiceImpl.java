package com.tkog.backend.service.impl.user;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.user.GetInfoService;
import com.tkog.backend.utils.GetUser;
import org.springframework.stereotype.Service;

@Service
public class GetInfoServiceImpl implements GetInfoService {
    @Override
    public JSONObject getInfo() {
        User user = GetUser.getUser();
        JSONObject resp = new JSONObject();

        if(user == null) {
            resp.put("message", "403");
            return resp;
        }

        resp.put("message", "success");
        resp.put("username", user.getUsername());
        resp.put("avatar", user.getAvatar());
        resp.put("id", user.getId());
        resp.put("rating", user.getRating());

        return resp;
    }
}
