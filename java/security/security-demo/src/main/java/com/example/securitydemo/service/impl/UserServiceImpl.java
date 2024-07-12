package com.example.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitydemo.config.DBUserDetailsManager;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.mapper.UserMapper;
import com.example.securitydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private DBUserDetailsManager dbUserDetailsManager;

    @Override
    public void saveUserDetail(User user) {
        //包成userDetails，符合dbUserDetailsManager.createUser
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withDefaultPasswordEncoder() //默認bcrypt，自適用單向函數
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        dbUserDetailsManager.createUser(userDetails);
    }
}
