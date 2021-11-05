package com.lmaye.ms.oauth.service;

import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.oauth.constants.GrandType;
import com.lmaye.ms.oauth.dto.LoginDTO;
import com.lmaye.ms.oauth.entity.AuthToken;

/**
 * -- Login Service
 *
 * @author lmay.Zhou
 * @date 2020/12/24 10:11
 * @email lmay@lmaye.com
 */
public interface LoginService {
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
    ResultVO<AuthToken> login(LoginDTO dto, String clientId, String clientSecret, GrandType grandType);
}
