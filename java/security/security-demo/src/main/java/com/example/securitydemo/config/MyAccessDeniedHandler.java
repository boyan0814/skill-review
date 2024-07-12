package com.example.securitydemo.config;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HashMap result = new HashMap();
        result.put("code", -1);
        result.put("messsage", "沒有權限");

        //將結果轉成json
        String json = JSON.toJSONString(result);

        //返回json到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
