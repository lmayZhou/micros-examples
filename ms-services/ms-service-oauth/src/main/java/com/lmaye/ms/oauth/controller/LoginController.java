package com.lmaye.ms.oauth.controller;

import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.oauth.constants.GrandType;
import com.lmaye.ms.oauth.dto.LoginDTO;
import com.lmaye.ms.oauth.entity.AuthToken;
import com.lmaye.ms.oauth.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "登录相关接口")
public class LoginController {
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
    @ApiOperation("用户登录")
    public ResultVO<AuthToken> login(@RequestBody LoginDTO dto) {
        return loginService.login(dto, "ms-oauth", "ms-oauth", GrandType.PASSWORD);
    }
}
