package com.lmaye.ms.gateway.web.config;

import com.lmaye.ms.gateway.web.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * -- Gateway Swagger Resources Provider
 *
 * @author Lmay Zhou
 * @date 2021/9/26 16:27
 * @email lmay@lmaye.com
 * @since JDK1.8
 */
@Primary
@Component
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {
    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;

    /**
     * Swagger 文档数据URI(※ Swagger3.0 basePath存在问题)
     */
    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     * 网关路由
     */
    @Autowired
    private RouteLocator routeLocator;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equals(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));
        Set<String> urls = new HashSet<>();
        routeHosts.forEach(instance -> {
            String url = "/" + instance.toLowerCase() + gatewayProperties.getSwaggerApiDocUri();
            if (!urls.contains(url)) {
                urls.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(instance);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }
}
