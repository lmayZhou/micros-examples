package com.lmaye.ms.service.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * -- Ms Service Oauth Application
 *
 * @author lmay.Zhou
 * @date 2020/12/21 17:07
 * @email lmay@lmaye.com
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OauthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }
}
