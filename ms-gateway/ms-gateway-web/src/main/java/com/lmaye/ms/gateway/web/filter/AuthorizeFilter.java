package com.lmaye.ms.gateway.web.filter;

import com.lmaye.ms.gateway.web.AuthProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * -- 全局过滤器 :用于鉴权(获取令牌 解析 判断)
 *
 * @author lmay.Zhou
 * @date 2020/12/23 17:51
 * @email lmay@lmaye.com
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    /**
     * Auth Properties
     */
    private final AuthProperties authProperties;

    public AuthorizeFilter(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    /**
     * 拦截
     *
     * @param exchange ServerWebExchange
     * @param chain    GatewayFilterChain
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        try {
            // 获取请求对象
            ServerHttpRequest request = exchange.getRequest();
            // 判断是否为登录的URL
            if (hasAutoresize(authProperties.getExcludeUrls(), request.getURI().getPath())) {
                // 放行
                return chain.filter(exchange);
            }
            // 从头header中获取令牌数据
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(authProperties.getAuthorizeToken());
            if (StringUtils.isEmpty(token)) {
                // 从cookie中中获取令牌数据
                HttpCookie first = request.getCookies().getFirst(authProperties.getAuthorizeToken());
                if (!Objects.isNull(first)) {
                    // 令牌的数据
                    token = first.getValue();
                }
            }
            if (StringUtils.isEmpty(token)) {
                // 从请求参数中获取令牌数据
                token = request.getQueryParams().getFirst(authProperties.getAuthorizeToken());
            }
            if (StringUtils.isEmpty(token)) {
                // 如果没有数据没有登录,要重定向到登录到页面(303 302)
                response.setStatusCode(HttpStatus.SEE_OTHER);
                // location 指定的就是路径
                response.getHeaders().set("Location", authProperties.getOauthLogin() + "?From=" + request.getURI().toString());
                return response.setComplete();
            }
            final boolean hasBearer = token.startsWith("bearer") || token.startsWith("Bearer");
            // 添加头信息
            request.mutate().header(authProperties.getAuthorizeToken(), hasBearer ? token : "Bearer " + token);
            return chain.filter(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }

    /**
     * 将通配符表达式转化为正则表达式
     *
     * @param path 路径
     * @return String
     */
    private static String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for (int i = 0; i < len; i++) {
            if (chars[i] == '*') {
                // 遇到*字符
                if (preX) {
                    // 如果是第二次遇到*，则将**替换成.*
                    sb.append(".*");
                    preX = false;
                } else if (i + 1 == len) {
                    // 如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                    sb.append("[^/]*");
                } else {
                    // 否则单星后面还有字符，则不做任何动作，下一把再做动作
                    preX = true;
                }
            } else {
                // 遇到非*字符
                if (preX) {
                    //如果上一把是*，则先把上一把的*对应的[^/]*添进来
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?') {
                    // 接着判断当前字符是不是?，是的话替换成.
                    sb.append('.');
                } else {
                    // 不是?的话，则就是普通字符，直接添进来
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 通配符模式
     *
     * @param excludePath 不过滤地址
     * @param reqUrl      请求地址
     * @return boolean
     */
    private static boolean filterUrls(String excludePath, String reqUrl) {
        return Pattern.compile(getRegPath(excludePath)).matcher(reqUrl).matches();
    }

    /**
     * 当前的请求是否放行
     *
     * @param excludeUrls 排除的URL
     * @param reqUrl      请求URL
     * @return boolean
     */
    private static boolean hasAutoresize(List<String> excludeUrls, String reqUrl) {
        return excludeUrls.stream().anyMatch(url -> filterUrls(url, reqUrl));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
