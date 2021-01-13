package com.lmaye.ms.service.oauth.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

/**
 * -- Admin Token Utils
 *
 * @author lmay.Zhou
 * @date 2020/12/23 11:47
 * @email lmay@lmaye.com
 */
public final class AdminTokenUtils {
    /**
     * 生成管理员Token令牌
     *
     * @param payload 载荷
     * @return String
     */
    public static String adminToken(Map<String, Object> payload) {
        // 加载证书
        ClassPathResource resource = new ClassPathResource("sso-service.jks");
        // 读取证书数据
        KeyStoreKeyFactory storeKeyFactory = new KeyStoreKeyFactory(resource, "sso#oauth".toCharArray());
        // 获取证书密钥对
        KeyPair keyPair = storeKeyFactory.getKeyPair("sso-service", "sso#oauth".toCharArray());
        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 创建令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));
        return jwt.getEncoded();
    }
}
