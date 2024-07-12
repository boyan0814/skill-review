package com.example.securitydemo.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //配置類
//@EnableWebSecurity //開啟spring security的自定義配置(可省略)
public class WebSecurityConfig {

    /*@Bean
    public UserDetailsService userDetailsService() {
        //創建基於內存的用戶訊息管理器
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        //用manager管理User
        manager.createUser(
                //創建UserDetail，會覆蓋掉application.properties中的設定
                User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build()
        );
        return manager;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //創建基於DB的用戶訊息管理器，也可以在DBUserDetailsManager加@Component就好
        DBUserDetailsManager manager = new DBUserDetailsManager();
        return manager;
    }*/

    @Bean //默認過濾器鏈
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //開啟授權保護
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest() //對所有請求開啟授權保護
                        .authenticated() //已認證請求自動授權
                )
                .formLogin(form -> {
                    form.loginPage("/login").permitAll() //用自定義的登入頁面，並關掉保護
                            // .usernameParameter("myusername") //如果要改參數名(<input type="text" name="myusername")
                            // .passwordParameter("mypassword");
                            // .failureUrl("/login?error"); //默認失敗跳轉點
                            .successHandler(new MyAuthenticationSuccessHandler())
                            .failureHandler(new MyAuthenticationFailureHandler()); //認證成功時的處裡
                });
        // .formLogin(withDefaults()); //使用表單授權方式(默認的登入、登出頁面)
        // .httpBasic(withDefaults()); //使用基本授權方式

        http.logout(logout -> logout.logoutSuccessHandler(new MyLogoutSuccessHandler())); //登出處裡
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(new MyAuthenticationEntryPoint())); //請求未認證處理

        http.csrf(csrf -> csrf.disable()); //要測swagger，先關掉
        return http.build();
    }
}