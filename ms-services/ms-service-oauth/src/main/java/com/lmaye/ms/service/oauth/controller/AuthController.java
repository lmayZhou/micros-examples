package com.lmaye.ms.service.oauth.controller;

import com.lmaye.ms.core.context.ResultVO;
import com.lmaye.ms.service.oauth.entity.AuthToken;
import com.lmaye.ms.service.oauth.service.AuthService;
import com.lmaye.ms.service.oauth.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * -- Auth Controller
 *
 * @author lmay.Zhou
 * @date 2020/12/23 16:27
 * @email lmay@lmaye.com
 */
@RestController
@RequestMapping(value = "/userx")
public class AuthController {
    //客户端ID
    @Value("${auth.clientId}")
    private String clientId;

    //秘钥
    @Value("${auth.clientSecret}")
    private String clientSecret;

    //Cookie存储的域名
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    //Cookie生命周期
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Autowired
    AuthService authService;

    @GetMapping("/login")
    public ResultVO<Object> login(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("用户名不允许为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("密码不允许为空");
        }
        //申请令牌
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);

        //用户身份令牌
        String access_token = authToken.getAccessToken();
        //将令牌存储到cookie
        saveCookie(access_token);

        return ResultVO.success("登录成功！");
    }

    /***
     * 将令牌存储到cookie
     * @param token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", "Authorization", token, cookieMaxAge, false);
    }
}
