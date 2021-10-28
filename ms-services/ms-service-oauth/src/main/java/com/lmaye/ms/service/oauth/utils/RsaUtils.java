package com.lmaye.ms.service.oauth.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * -- Rsa Utils 加密工具
 *
 * @author lmay.Zhou
 * @date 2021/6/1 15:30
 * @email lmay@lmaye.com
 * @since JDK1.8
 */
public final class RsaUtils {
    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;

    /**
     * 随机生成密钥对
     *
     * @return Map<String, String>
     * @throws NoSuchAlgorithmException 异常
     */
    public static Map<String, String> genKeyPair() throws NoSuchAlgorithmException {
        Map<String, String> keys = new HashMap<>(2);
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        // 保存公钥和私钥
//        keys.put("pubKey", Base64.getEncoder().encodeToString(pubKey.getEncoded()));
//        keys.put("priKey", Base64.getEncoder().encodeToString(priKey.getEncoded()));
        keys.put("pubKey", Base64.encodeBase64String(pubKey.getEncoded()));
        keys.put("priKey", Base64.encodeBase64String(priKey.getEncoded()));
        return keys;
    }

    public static Map<String, String> getKeyPair() {
        Map<String, String> keys = new HashMap<>(2);
        // 加载证书
        ClassPathResource resource = new ClassPathResource("ms-service-oauth.jks");
        // 读取证书数据
        KeyStoreKeyFactory storeKeyFactory = new KeyStoreKeyFactory(resource, "ms-oauth".toCharArray());
        // 获取证书密钥对
        KeyPair keyPair = storeKeyFactory.getKeyPair("ms-service-oauth", "ms-oauth".toCharArray());
        // 获取私钥
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        // 保存公钥和私钥
        keys.put("pubKey", Base64.encodeBase64String(pubKey.getEncoded()));
        keys.put("priKey", Base64.encodeBase64String(priKey.getEncoded()));
        return keys;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str);
        // base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }
}
