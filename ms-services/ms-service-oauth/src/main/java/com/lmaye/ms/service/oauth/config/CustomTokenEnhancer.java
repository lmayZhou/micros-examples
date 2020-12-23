package com.lmaye.ms.service.oauth.config;

import com.lmaye.ms.service.oauth.vo.UserTokenVo;
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
        // 这个UserTokenVo就是之前UserDetail返回的对象
        //从那获取要增强携带的字段
//        UserTokenVo user = (UserTokenVo) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>(2);
        //添加token携带的字段
        info.put("id", "10000");
        info.put("nickname", "Lmay");
        info.put("description", "www.lmaye.com");
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        token.setAdditionalInformation(info);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
