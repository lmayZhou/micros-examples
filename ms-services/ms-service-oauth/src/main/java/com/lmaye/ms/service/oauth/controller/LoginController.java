package com.lmaye.ms.service.oauth.controller;

import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.service.oauth.dto.LoginDTO;
import com.lmaye.ms.service.oauth.entity.AuthToken;
import com.lmaye.ms.service.oauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * Client Id
     */
    @Value("${auth.clientId}")
    private String clientId;

    /**
     * Client Secret
     */
    @Value("${auth.clientSecret}")
    private String clientSecret;

    /**
     * 授权模式: 密码模式
     */
    private static final String GRAND_TYPE = "password";

    /**
     * Login Service
     */
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     * - 密码授权模式
     *
     * @param dto     LoginDTO
     * @return ResultVO<AuthToken>
     */
    @PostMapping("/login")
    public ResultVO<AuthToken> login(@RequestBody LoginDTO dto) {
        return loginService.login(dto, clientId, clientSecret, GRAND_TYPE);
    }
}
