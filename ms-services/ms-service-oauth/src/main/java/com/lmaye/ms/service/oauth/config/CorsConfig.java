package com.lmaye.ms.service.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * -- 跨域配置
 *
 * @author lmay.Zhou
 * @date 2020/12/22 14:36
 * @email lmay@lmaye.com
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH").maxAge(3600);
    }
}
