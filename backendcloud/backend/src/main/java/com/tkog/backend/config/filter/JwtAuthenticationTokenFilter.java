package com.tkog.backend.config.filter;

import com.alibaba.fastjson2.JSONObject;
import com.tkog.backend.enums.AppHttpCodeEnum;
import com.tkog.backend.mapper.UserMapper;
import com.tkog.backend.pojo.User;
import com.tkog.backend.service.impl.utils.UserDetailsImpl;
import com.tkog.backend.utils.JwtUtil;
import org.jetbrains.annotations.NotNull;
import com.tkog.backend.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserMapper userMapper;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return ;
        }

        token = token.substring(7);

        String userid = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            JSONObject result = new JSONObject();
            result.put("code", AppHttpCodeEnum.NEED_LOGIN.getCode());
            result.put("message", AppHttpCodeEnum.NEED_LOGIN.getMessage());
            WebUtils.renderString(response, result.toJSONString());
            return;
        }

        // 通过userId来查找用户
        User user = userMapper.selectById(Integer.parseInt(userid));

        // 用户不存在
        if(user == null) {
            JSONObject result = new JSONObject();
            result.put("code", AppHttpCodeEnum.NEED_LOGIN.getCode());
            result.put("message", AppHttpCodeEnum.NEED_LOGIN.getMessage());
            WebUtils.renderString(response, result.toJSONString());
            return;
        }

        UserDetailsImpl loginUser = new UserDetailsImpl(user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
