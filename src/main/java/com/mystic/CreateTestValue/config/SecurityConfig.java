package com.mystic.CreateTestValue.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author mystic
 * @date 2022/10/15 17:36
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//       解决spring security 禁止post访问, 关闭csrf即可
        http.csrf().disable();
//        继承默认资源不放行,否则不会跳转登录界面
        super.configure(http);
    }
}
