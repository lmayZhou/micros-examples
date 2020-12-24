package com.lmaye.ms.service.oauth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lmaye.ms.core.context.ResultCode;
import com.lmaye.ms.core.exception.ServiceException;
import com.lmaye.ms.service.oauth.dto.LoginDTO;
import com.lmaye.ms.service.oauth.entity.AuthToken;
import com.lmaye.ms.service.oauth.service.LoginService;
import com.lmaye.ms.service.oauth.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    /**
     * Rest Template
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Load Balancer Client
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * Cookie Domain
     */
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    /**
     * Cookie生命周期
     */
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    /**
     * 用户登录
     * - 密码授权模式
     *
     * @param dto          LoginDTO
     * @param clientId     clientId
     * @param clientSecret clientSecret
     * @param grandType    grandType
     * @return AuthToken
     */
    @Override
    public AuthToken login(LoginDTO dto, String clientId, String clientSecret, String grandType) {
        // 微服务的名称spring.application.name
        ServiceInstance choose = loadBalancerClient.choose("oauth2-service");
        // 1.定义url(申请令牌的url)
        String url = choose.getUri().toString() + "/oauth/token";
        // 2.定义头信息(有Client Id 和Client Secret)
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
        // 3.定义请求体有授权模式用户的名称和密码
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grandType);
        formData.add("username", dto.getUsername());
        formData.add("password", dto.getPassword());
        // 4.模拟浏览器发送POST请求携带头和请求体到认证服务器
        HttpEntity<MultiValueMap<?, ?>> httpEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
        // 5.接收到返回的响应(就是:令牌的信息)
        JSONObject body = responseEntity.getBody();
        if (Objects.isNull(body)) {
            throw new ServiceException(ResultCode.UNAUTHORIZED);
        }
        AuthToken authToken = new AuthToken();
        authToken.setJti(body.getString("jti"));
        authToken.setAccessToken(body.getString("access_token"));
        authToken.setRefreshToken(body.getString("refresh_token"));
        authToken.setExpiresIn(body.getInteger("expires_in"));
        authToken.setVerifyEnable(body.getBoolean("verifyEnable"));
        // 设置到cookie中
        saveCookie(authToken.getAccessToken());
        return authToken;
    }

    /**
     * 保存Cookie
     *
     * @param token token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        if (!Objects.isNull(response)) {
            CookieUtil.addCookie(response, cookieDomain, "/", "Authorization", token, cookieMaxAge, false);
        }
    }
}
