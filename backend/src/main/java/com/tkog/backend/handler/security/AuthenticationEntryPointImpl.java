package com.tkog.backend.handler.security;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        JSONObject result = new JSONObject();

        System.out.println("123");

        if(authException instanceof InsufficientAuthenticationException) {
            result.put("code", 401);
            result.put("message", "用户未登录");
        } else if(authException instanceof BadCredentialsException) {
            result.put("code", 504);
            result.put("message", "用户名或者密码错误");
        } else {
            result.put("code", 500);
            result.put("message", "服务器错误");
        }

        WebUtils.renderString(response, result.toJSONString());
    }
}
