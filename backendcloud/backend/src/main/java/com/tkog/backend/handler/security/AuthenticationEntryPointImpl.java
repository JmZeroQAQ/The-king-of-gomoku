package com.tkog.backend.handler.security;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.enums.AppHttpCodeEnum;
import com.tkog.backend.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        JSONObject result = new JSONObject();
        if(authException instanceof InsufficientAuthenticationException) {
            result.put("code", AppHttpCodeEnum.NEED_LOGIN.getCode());
            result.put("message", AppHttpCodeEnum.NEED_LOGIN.getMessage());
        } else if(authException instanceof BadCredentialsException) {
            result.put("code", AppHttpCodeEnum.LOGIN_ERROR.getCode());
            result.put("message", AppHttpCodeEnum.LOGIN_ERROR.getMessage());
        } else {
            result.put("code", AppHttpCodeEnum.SYSTEM_ERROR.getCode());
            result.put("message", AppHttpCodeEnum.SYSTEM_ERROR.getMessage());
        }

        WebUtils.renderString(response, result.toJSONString());
    }
}
