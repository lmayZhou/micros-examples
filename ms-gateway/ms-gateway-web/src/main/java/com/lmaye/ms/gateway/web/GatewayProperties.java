package com.lmaye.ms.gateway.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * -- Auth Properties
 *
 * @author Lmay Zhou
 * @date 2021/5/24 16:53
 * @email lmay@lmaye.com
 */
@Data
@ConfigurationProperties(prefix = "ms-gateway")
public class GatewayProperties {
    /**
     * Token 标识
     */
    private String authorizeToken = "Authorization";

    /**
     * Swagger 文档数据URI
     */
    private String swaggerApiDocUri = "/v2/api-docs";

    /**
     * 放行的请求路径
     */
    private List<String> excludeUrls;

    /**
     * 认证登录地址
     */
    private String oauthLogin;
}
