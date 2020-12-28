package com.lmaye.ms.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * -- 全局过滤器 :用于鉴权(获取令牌 解析 判断)
 *
 * @author lmay.Zhou
 * @date 2020/12/23 17:51
 * @email lmay@lmaye.com
 */
public class AuthorizeFilter implements GlobalFilter, Ordered {
    /**
     * Authorize Token
     */
    private static final String AUTHORIZE_TOKEN = "Authorization";

    /**
     * No Intercept URL
     */
    private static final String NO_INTERCEPT_URL = "/api/user/login,/api/user/add";

    /**
     * OAuth Login
     */
    private static final String OAUTH_LOGIN = "http://localhost:9001/oauth/login";

    /**
     * 拦截
     *
     * @param exchange ServerWebExchange
     * @param chain    GatewayFilterChain
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 2.获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 3.判断 是否为登录的URL 如果是 放行
        if (hasAutoresize(request.getURI().toString())) {
            return chain.filter(exchange);
        }
        // .判断 是否为登录的URL 如果不是      权限校验
        // 4.1 从头header中获取令牌数据
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isEmpty(token)) {
            // 4.2 从cookie中中获取令牌数据
            HttpCookie first = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (!Objects.isNull(first)) {
                // 就是令牌的数据
                token = first.getValue();
            }
        }
        if (StringUtils.isEmpty(token)) {
            // 4.3 从请求参数中获取令牌数据
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        if (StringUtils.isEmpty(token)) {
            // 4.4. 如果没有数据没有登录,要重定向到登录到页面(303 302)
            response.setStatusCode(HttpStatus.SEE_OTHER);
            //location 指定的就是路径
            response.getHeaders().set("Location", OAUTH_LOGIN + "?From=" + request.getURI().toString());
            return response.setComplete();
        }
        // 5 解析令牌数据 ( 判断解析是否正确,正确 就放行 ,否则 结束)
        /*try {
            Claims claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }*/
        // 添加头信息 传递给 各个微服务()
        request.mutate().header(AUTHORIZE_TOKEN, "Bearer " + token);
        return chain.filter(exchange);
    }

    /**
     * 用来判断 如果 当前的请求 在 放行的请求中存在,(不需要拦截 :true,否则需要拦截:false)
     *
     * @param uri URI
     * @return boolean
     */
    public static boolean hasAutoresize(String uri) {
        String[] split = NO_INTERCEPT_URL.split(",");
        for (String s : split) {
            if (s.equals(uri)) {
                // 不需要拦截
                return true;
            }
        }
        // 要拦截
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
