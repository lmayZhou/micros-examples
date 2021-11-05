package com.lmaye.ms.oauth.service;

import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.oauth.constants.GrandType;
import com.lmaye.ms.oauth.dto.AuthDTO;
import com.lmaye.ms.oauth.entity.AuthToken;

/**
 * -- Auth Service
 *
 * @author lmay.Zhou
 * @date 2020/12/24 10:11
 * @email lmay@lmaye.com
 */
public interface IAuthService {
    /**
     * 获取授权认证的Token令牌
     * - 密码授权模式
     *
     * @param dto       AuthDTO
     * @param grandType grandType
     * @return ResultVO<AuthToken>
     */
    ResultVO<AuthToken> token(AuthDTO dto, GrandType grandType);

    /**
     * 刷新授权认证的Token令牌
     *
     * @param clientId     客户端ID，分配给应用的AppId
     * @param clientSecret 客户端密钥，分配给网站的AppKey
     * @param refreshToken 刷新Token
     * @return AuthToken
     */
    AuthToken refreshToken(String clientId, String clientSecret, String refreshToken);

    /**
     * 续期授权认证的Token令牌
     *
     * @param accessToken 令牌信息
     * @return Boolean
     */
    Boolean renewToken(String accessToken);

    /**
     * 清除Token
     *
     * @param accessToken 令牌信息
     * @return Boolean
     */
    Boolean clearToken(String accessToken);
}
