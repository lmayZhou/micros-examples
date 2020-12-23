package com.lmaye.ms.service.oauth.controller;

import com.lmaye.ms.core.context.ResultVO;
import com.lmaye.ms.service.oauth.entity.AuthToken;
import com.lmaye.ms.service.oauth.service.LoginService;
import com.lmaye.ms.service.oauth.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * -- Login Controller
 *
 * @author lmay.Zhou
 * @date 2020/12/22 17:38
 * @email lmay@lmaye.com
 */
@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    /**
     * 授权模式: 密码模式
     */
    private static final String GRAND_TYPE = "password";

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    /**
     * Cookie生命周期
     */
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    /**
     * 授权认证
     * - 密码模式
     *
     * @param username username
     * @param password password
     * @return ResultVO<Object>
     */
    @RequestMapping("/login")
    public ResultVO<Object> login(String username, String password) {
        // 登录 之后生成令牌的数据返回
        AuthToken authToken = loginService.login(username, password, clientId, clientSecret, GRAND_TYPE);
        // 设置到cookie中
        saveCookie(authToken.getAccessToken());
        return ResultVO.success(authToken);
    }

    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        assert response != null;
        CookieUtil.addCookie(response, cookieDomain, "/", "Authorization", token, cookieMaxAge, false);
    }
}
