package com.lmaye.ms.service.oauth.service;

import com.lmaye.ms.core.context.ResultVO;
import com.lmaye.ms.service.oauth.dto.LoginDTO;
import com.lmaye.ms.service.oauth.entity.AuthToken;

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
    ResultVO<AuthToken> login(LoginDTO dto, String clientId, String clientSecret, String grandType);
}
