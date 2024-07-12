package com.example.securitydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@SpringBootTest
class SecurityDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testPassword() {
        //工作因子默認是10，最大31。越大速度越慢，避免暴力破解。
        PasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String result = encoder.encode("pass");
        System.out.println(result);

        //較驗
        Assert.isTrue(encoder.matches("pass", result), "密碼不一致");
    }

}
