package com.lmaye.ms.gateway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * -- Web Gateway Application
 *
 * @author lmay.Zhou
 * @date 2020/12/28 22:49 星期一
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since JDK1.8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(GatewayProperties.class)
public class WebGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebGatewayApplication.class, args);
    }
}
