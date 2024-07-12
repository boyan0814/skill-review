package com.example.securitydemo.config;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String localizedMessage = exception.getLocalizedMessage();

        HashMap result = new HashMap();
        result.put("code", -1);
        result.put("messsage", localizedMessage);

        //將結果轉成json
        String json = JSON.toJSONString(result);

        //返回json到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
