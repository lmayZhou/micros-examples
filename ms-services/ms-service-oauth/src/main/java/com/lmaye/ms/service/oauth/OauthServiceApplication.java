package com.lmaye.ms.service.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * -- Ms Service Oauth Application
 *
 * @author lmay.Zhou
 * @date 2020/12/21 17:07
 * @email lmay@lmaye.com
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lmaye.ms.services.api.user.feign"})
public class OauthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }

    /**
     * Rest Template
     *
     * @return RestTemplate
     */
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
