package com.tkog.backend.handler.security;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        JSONObject result = new JSONObject();
        result.put("code", 403);
        result.put("message", "无权限操作");

        WebUtils.renderString(response, result.toJSONString());
    }
}
