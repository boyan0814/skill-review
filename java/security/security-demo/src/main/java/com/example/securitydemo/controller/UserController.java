package com.example.securitydemo.controller;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'admin'") //沒配置默認能訪問，有配置就會檢查角色
    public List<User> getList(){
        return userService.list();
    }

    // post用swagger測:http://localhost:8080/demo/swagger-ui.html
    // 默認開啟csrf，要記得關掉
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody User user) {
        userService.saveUserDetail(user);
    }
}
