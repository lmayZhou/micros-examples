package com.lmaye.ms.service.oauth.service.impl;

import cn.hutool.json.JSONObject;
import com.lmaye.cloud.core.context.ResultCode;
import com.lmaye.cloud.core.exception.ServiceException;
import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.service.oauth.constants.OauthConstants;
import com.lmaye.ms.service.oauth.constants.OAuthResultCode;
import com.lmaye.ms.service.oauth.dto.LoginDTO;
import com.lmaye.ms.service.oauth.entity.AuthToken;
import com.lmaye.ms.service.oauth.service.LoginService;
import com.lmaye.ms.service.oauth.utils.CookieUtil;
import com.lmaye.ms.service.user.api.entity.SysUser;
import com.lmaye.ms.service.user.api.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * -- Login Service Impl
 *
 * @author lmay.Zhou
 * @date 2020/12/23 14:30
 * @email lmay@lmaye.com
 */
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
     * User Feign
     */
    @Autowired
    private UserFeign userFeign;

    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Redis Template
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登录
     * - 密码授权模式
     *
     * @param dto          LoginDTO
     * @param clientId     clientId
     * @param clientSecret clientSecret
     * @param grandType    grandType
     * @return ResultVO<AuthToken>
     */
    @Override
    public ResultVO<AuthToken> login(LoginDTO dto, String clientId, String clientSecret, String grandType) {
        try {
            AuthToken authToken = new AuthToken();
            authToken.setEnableCaptcha(false);
            // 先校验用户名、密码, 密码错误3次开启验证码
            String username = dto.getUsername();
            String password = dto.getPassword();
            ResultVO<SysUser> result = userFeign.queryByUserName(username);
            SysUser user = result.getData();
            if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) {
                // 用户密码错误
                String key = OauthConstants.ENABLE_CAPTCHA_KEY_PREFIX + username;
                Boolean hasKey = redisTemplate.hasKey(key);
                if (!Objects.isNull(hasKey) && !hasKey) {
                    redisTemplate.opsForValue().set(key, 1, 1, TimeUnit.HOURS);
                } else {
                    redisTemplate.opsForValue().increment(key);
                    Object total = redisTemplate.opsForValue().get(key);
                    if (!Objects.isNull(total) && Integer.parseInt(total.toString()) >= OauthConstants.TRY_TIMES) {
                        authToken.setEnableCaptcha(true);
                    }
                }
                return ResultVO.response(OAuthResultCode.USER_VERIFICATION_FAILURE, authToken);
            }
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
            formData.add("username", username);
            formData.add("password", password);
            // 4.模拟浏览器发送POST请求携带头和请求体到认证服务器
            HttpEntity<MultiValueMap<?, ?>> httpEntity = new HttpEntity<>(formData, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            // 5.接收到返回的响应(就是:令牌的信息)
            JSONObject body = responseEntity.getBody();
            if (Objects.isNull(body)) {
                throw new ServiceException(ResultCode.UNAUTHORIZED);
            }
            authToken.setJti(body.getStr("jti"));
            authToken.setAccessToken(body.getStr("access_token"));
            authToken.setRefreshToken(body.getStr("refresh_token"));
            authToken.setExpiresIn(body.getInt("expires_in"));
            authToken.setTokenType(body.getStr("token_type"));
            // 设置到cookie中
            saveCookie(authToken.getAccessToken());
            return ResultVO.success(authToken);
        } catch (RestClientException e) {
            throw new ServiceException(ResultCode.UNAUTHORIZED);
        }
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
