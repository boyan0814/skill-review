package com.example.securitydemo.config;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principle = authentication.getPrincipal(); //用戶身分訊息

        /*Object credentials = authentication.getCredentials(); //用戶憑證訊息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();*/

        HashMap result = new HashMap();
        result.put("code", 0);
        result.put("messsage", "登入成功");
        result.put("data", principle);

        //將結果轉成json
        String json = JSON.toJSONString(result);

        //返回json到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
