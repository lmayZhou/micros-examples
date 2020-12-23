package com.lmaye.ms.service.oauth.config;

import com.lmaye.ms.service.oauth.service.OauthUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * -- Web Security Config
 *
 * @author lmay.Zhou
 * @date 2020/12/22 14:54
 * @email lmay@lmaye.com
 */
@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * SpringSecurity 用户自定义授权认证类
     */
    @Autowired
    private OauthUserDetailsServiceImpl userDetailsService;

    /**
     * 注入密码加密
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 重新注入认证管理器
     *
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 忽略安全拦截的URL
     *
     * @param security WebSecurity
     * @throws Exception 异常
     */
    @Override
    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/user/login", "/user/logout", "/oauth/login", "/css/**", "/userx/login",
                "/data/**", "/fonts/**", "/img/**", "/js/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**")
                .permitAll().anyRequest().authenticated().and().formLogin().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义方式加载用户信息
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
