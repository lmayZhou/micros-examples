package com.lmaye.ms.oauth.config;

import com.lmaye.ms.oauth.entity.UserToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * -- 增强颁发的token的携带信息
 *
 * @author lmay.Zhou
 * @date 2020/12/22 18:29
 * @email lmay@lmaye.com
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 增强携带的字段
        UserToken user = (UserToken) authentication.getPrincipal();
        Map<String, Object> information = new HashMap<>(1);
        // 添加token携带的字段
        information.put("id", user.getId());
        information.put("nickname", user.getUsername());
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        token.setAdditionalInformation(information);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(information);
        return accessToken;
    }
}
