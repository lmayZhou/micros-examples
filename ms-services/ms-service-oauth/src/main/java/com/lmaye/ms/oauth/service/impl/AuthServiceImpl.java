package com.lmaye.ms.oauth.service.impl;

import cn.hutool.json.JSONObject;
import com.lmaye.cloud.core.context.ResultCode;
import com.lmaye.cloud.core.exception.ServiceException;
import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.oauth.constants.OAuthResultCode;
import com.lmaye.ms.oauth.service.IAuthService;
import com.lmaye.ms.oauth.utils.RsaUtils;
import com.lmaye.ms.oauth.constants.GrandType;
import com.lmaye.ms.oauth.constants.OauthConstants;
import com.lmaye.ms.oauth.dto.AuthDTO;
import com.lmaye.ms.oauth.entity.AuthToken;
import com.lmaye.ms.oauth.properties.OauthProperties;
import com.lmaye.ms.user.api.UserFeign;
import com.lmaye.ms.user.api.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * -- Auth Service Impl
 *
 * @author lmay.Zhou
 * @date 2020/12/23 14:30
 * @email lmay@lmaye.com
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    /**
     * Rest Template
     */
    @Autowired
    private RestTemplate restTemplate;

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
     * Load Balancer Client
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * Oauth Properties
     */
    @Autowired
    private OauthProperties oauthProperties;

    /**
     * Token Store
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * User Feign
     */
    @Autowired
    private UserFeign userFeign;

    /**
     * Application Name
     */
    @Value("${spring.application.name}")
    private String appName;

    /**
     * 获取授权认证的Token令牌
     * - 密码授权模式
     *
     * @param dto       AuthDTO
     * @param grandType grandType
     * @return ResultVO<AuthToken>
     */
    @Override
    public ResultVO<AuthToken> token(AuthDTO dto, GrandType grandType) {
        AuthToken authToken = new AuthToken();
        authToken.setEnableCaptcha(false);
        String key = OauthConstants.ENABLE_CAPTCHA_KEY_PREFIX + dto.getUsername();
        // 校验验证码
        ResultVO<AuthToken> checkRs = checkCaptcha(key, dto.getVerifyCode(), dto.getKey(), authToken);
        if (!Objects.equals(ResultCode.SUCCESS.getCode(), checkRs.getCode())) {
            return checkRs;
        }
        // 获取用户信息
        ResultVO<SysUser> result = userFeign.queryByUserName(dto.getUsername());
        SysUser user = result.getData();
        if (Objects.isNull(user)) {
            throw new ServiceException(OAuthResultCode.USER_VERIFICATION_FAILURE);
        }
        // 获取请求体
        MultiValueMap<String, String> formData = getFormData(authToken, grandType, dto, key, user.getPassword());
        // 认证处理
        authenticationProcessing(dto.getClientId(), dto.getClientSecret(), authToken, formData);
        return ResultVO.success(authToken);
    }

    /**
     * 刷新授权认证的Token令牌
     *
     * @param clientId     客户端ID，分配给应用的AppId
     * @param clientSecret 客户端密钥，分配给网站的AppKey
     * @param refreshToken 刷新Token
     * @return AuthToken
     */
    @Override
    public AuthToken refreshToken(String clientId, String clientSecret, String refreshToken) {
        AuthToken authToken = new AuthToken();
        authToken.setEnableCaptcha(false);
        // 定义请求体有授权模式REFRESH_TOKEN
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GrandType.REFRESH_TOKEN.name().toLowerCase(Locale.ROOT));
        formData.add("refresh_token", refreshToken);
        // 认证处理
        authenticationProcessing(clientId, clientSecret, authToken, formData);
        return authToken;
    }

    /**
     * 续期授权认证的Token令牌
     * - JwtTokenStore 方式无效
     *
     * @param accessToken 令牌信息
     * @return Boolean
     */
    @Override
    public Boolean renewToken(String accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) tokenStore.readAccessToken(accessToken);
        if(Objects.isNull(token)) {
            throw new ServiceException(ResultCode.UNAUTHORIZED);
        }
        OAuth2Authentication authentication = tokenStore.readAuthentication(token);
        // 续期1H
        token.setExpiration(new Date(System.currentTimeMillis() + (oauthProperties.getExpiresIn() * 1000L)));
        tokenStore.storeAccessToken(token, authentication);
        return true;
    }

    /**
     * 清除Token
     * - JwtTokenStore 方式无效
     *
     * @param accessToken 令牌信息
     * @return Boolean
     */
    @Override
    public Boolean clearToken(String accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) tokenStore.readAccessToken(accessToken);
        if(Objects.isNull(token)) {
            throw new ServiceException(ResultCode.UNAUTHORIZED);
        }
        tokenStore.removeAccessToken(token);
        tokenStore.removeRefreshToken(token.getRefreshToken());
        return true;
    }

    /**
     * 校验验证码
     *
     * @param key        缓存Key
     * @param verifyCode 验证码Code
     * @param verifyKey  验证码Key
     * @return ResultVO<AuthToken>
     */
    private ResultVO<AuthToken> checkCaptcha(String key, String verifyCode, String verifyKey, AuthToken authToken) {
        // 获取缓存
        Object times = redisTemplate.opsForValue().get(key);
        if (Integer.parseInt(Objects.isNull(times) ? "0" : times.toString()) >= OauthConstants.TRY_TIMES) {
            if (StringUtils.isEmpty(verifyCode)) {
                authToken.setEnableCaptcha(false);
                return ResultVO.response(OAuthResultCode.CAPTCHA_EMPTY, authToken);
            }
            // TODO 校验验证码
            /*if (!true) {
                authToken.setEnableCaptcha(false);
                return ResultVO.response(OAuthResultCode.CAPTCHA_ERROR, authToken);
            }*/
        }
        return ResultVO.success(authToken);
    }

    /**
     * 获取请求体
     *
     * @param authToken AuthToken
     * @param grandType GrandType
     * @param dto       AuthDTO
     * @param key       缓存Key
     * @param pwd       用户密码
     * @return MultiValueMap<String, String>
     */
    private MultiValueMap<String, String> getFormData(AuthToken authToken, GrandType grandType, AuthDTO dto,
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
