package com.tkog.backend.service.impl.user;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkog.backend.mapper.UserMapper;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public JSONObject register(String username, String password, String confirmedPassword) {
        JSONObject resp = new JSONObject();

        if(username == null) {
            resp.put("message", "用户名不能为空");
            return resp;
        }

        if(password == null || confirmedPassword == null) {
            resp.put("message", "密码不能为空");
        }

        // 去掉首尾空格回车
        username = username.trim();
        if(username.length() == 0) {
            resp.put("message", "用户名不能为空");
            return resp;
        }

        if(password.length() == 0 || confirmedPassword.length() == 0) {
            resp.put("message", "密码不能为空");
            return resp;
        }

        if(username.length() > 100) {
            resp.put("message", "用户名长度不能大于100");
            return resp;
        }

        if(password.length() > 24 || confirmedPassword.length() > 24) {
            resp.put("message", "密码长度不能大于24");
            return resp;
        }

        if(!password.equals(confirmedPassword)) {
            resp.put("message", "两次输入的密码不一致");
            return resp;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()) {
            resp.put("message", "用户名已存在");
            return resp;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String avatar = "https://ranunculus.top/media/images/20221111175900100.png";
        User user = new User(null, username, encodedPassword, avatar, 1500);
        userMapper.insert(user);

        resp.put("message", "success");
        return resp;
    }
}
