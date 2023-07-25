package com.tkog.backend.service.impl.user;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.impl.utils.UserDetailsImpl;
import com.tkog.backend.service.user.LoginService;
import com.tkog.backend.utils.GetUser;
import com.tkog.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public JSONObject getToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String jwt = JwtUtil.createJWT(user.getId().toString());

        JSONObject resp = new JSONObject();
        resp.put("message", "success");
        resp.put("token", jwt);

        return resp;
    }
}
