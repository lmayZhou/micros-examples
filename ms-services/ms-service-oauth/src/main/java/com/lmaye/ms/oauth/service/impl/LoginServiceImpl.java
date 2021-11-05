package com.lmaye.ms.oauth.service.impl;

import cn.hutool.json.JSONObject;
import com.lmaye.cloud.core.context.ResultCode;
import com.lmaye.cloud.core.exception.ServiceException;
import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.oauth.service.LoginService;
import com.lmaye.ms.oauth.utils.CookieUtil;
import com.lmaye.ms.oauth.utils.RsaUtils;
import com.lmaye.ms.oauth.constants.GrandType;
import com.lmaye.ms.oauth.constants.OAuthResultCode;
import com.lmaye.ms.oauth.constants.OauthConstants;
import com.lmaye.ms.oauth.dto.LoginDTO;
import com.lmaye.ms.oauth.entity.AuthToken;
import com.lmaye.ms.oauth.properties.OauthProperties;
import com.lmaye.ms.user.api.UserFeign;
import com.lmaye.ms.user.api.entity.SysUser;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Locale;
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
     * Oauth Properties
     */
    @Autowired
    private OauthProperties oauthProperties;

    /**
     * Application Name
     */
    @Value("${spring.application.name}")
    private String appName;

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
    public ResultVO<AuthToken> login(LoginDTO dto, String clientId, String clientSecret, GrandType grandType) {
        try {
            AuthToken authToken = new AuthToken();
            authToken.setEnableCaptcha(false);
            // 先校验用户名、密码, 密码错误3次开启验证码
            String username = dto.getUsername();
            ResultVO<SysUser> result = userFeign.queryByUserName(username);
            SysUser user = result.getData();
            if (Objects.isNull(user)) {
                throw new ServiceException(OAuthResultCode.USER_VERIFICATION_FAILURE);
            }
            String key = OauthConstants.ENABLE_CAPTCHA_KEY_PREFIX + username;
            // 获取请求体
            MultiValueMap<String, String> formData = getFormData(authToken, grandType, dto, key, user.getPassword());
            // 认证处理
            authenticationProcessing(clientId, clientSecret, authToken, formData);
            // 设置到cookie中
            saveCookie(authToken.getAccessToken());
            return ResultVO.success(authToken);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNAUTHORIZED);
        }
    }

    /**
     * 保存Cookie
     *
     * @param token token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getResponse();
        if (!Objects.isNull(response)) {
            CookieUtil.addCookie(response, oauthProperties.getCookieDomain(), "/", "Authorization", token,
                    oauthProperties.getCookieMaxAge(), false);
        }
    }

    /**
     * 获取请求体
     *
     * @param authToken AuthToken
     * @param grandType GrandType
     * @param dto       LoginDTO
     * @param key       缓存Key
     * @param pwd       用户密码
     * @return MultiValueMap<String, String>
     */
    private MultiValueMap<String, String> getFormData(AuthToken authToken, GrandType grandType, LoginDTO dto,
                                                      String key, String pwd) {
        try {
            // 密码解密
            String password = RsaUtils.decrypt(dto.getPassword(), RsaUtils.getKeyPair().get("priKey"));
            Boolean hasKey = redisTemplate.hasKey(key);
            if (!passwordEncoder.matches(password, pwd)) {
                // 用户密码错误
                if (Objects.isNull(hasKey) || !hasKey) {
                    redisTemplate.opsForValue().set(key, 1, 30, TimeUnit.MINUTES);
                } else {
                    redisTemplate.opsForValue().increment(key);
                    Object times = redisTemplate.opsForValue().get(key);
                    if (!Objects.isNull(times) && Integer.parseInt(times.toString()) >= OauthConstants.TRY_TIMES) {
                        authToken.setEnableCaptcha(true);
                    }
                }
                throw new ServiceException(OAuthResultCode.USER_VERIFICATION_FAILURE);
            }
            if (!Objects.isNull(hasKey) && hasKey) {
                // 登录成功, 删除缓存Key(防止叠加)
                redisTemplate.delete(key);
            }
            // 定义请求体有授权模式用户的名称和密码
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", grandType.name().toLowerCase(Locale.ROOT));
            formData.add("username", dto.getUsername());
            formData.add("password", password);
            return formData;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNAUTHORIZED, e);
        }
    }

    /**
     * 认证处理
     *
     * @param clientId     客户端ID
     * @param clientSecret 客户端秘钥
     * @param authToken    AuthToken
     * @param formData     表单数据
     */
    private void authenticationProcessing(String clientId, String clientSecret, AuthToken authToken,
                                          MultiValueMap<String, String> formData) throws ServiceException {
        try {
            // 定义头信息(有Client Id 和Client Secret)
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":"
                    + clientSecret).getBytes()));
            // 模拟浏览器发送POST请求携带头和请求体到认证服务器
            HttpEntity<MultiValueMap<?, ?>> httpEntity = new HttpEntity<>(formData, headers);
            // 微服务的名称spring.application.name
            ServiceInstance choose = loadBalancerClient.choose(appName);
            // 定义url(申请令牌的url)
            String url = Objects.isNull(choose) ? oauthProperties.getDefaultTokenUrl() : choose.getUri().toString() + "/oauth/token";
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                    JSONObject.class);
            // 接收到返回的响应(令牌的信息)
            JSONObject body = responseEntity.getBody();
            if (Objects.isNull(body)) {
                throw new ServiceException(ResultCode.UNAUTHORIZED);
            }
            authToken.setJti(body.getStr("jti"));
            authToken.setAccessToken(body.getStr("access_token"));
            authToken.setRefreshToken(body.getStr("refresh_token"));
            authToken.setExpiresIn(body.getInt("expires_in"));
            authToken.setTokenType(body.getStr("token_type"));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNAUTHORIZED, e);
        }
    }
}
