package com.lmaye.ms.service.oauth.service;

import com.lmaye.ms.service.oauth.entity.AuthToken;

public interface AuthService {

    /***
     * 授权认证方法
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);
}
