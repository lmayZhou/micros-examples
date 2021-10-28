package com.lmaye.ms.service.oauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * -- Cookie Properties
 *
 * @author Lmay Zhou
 * @date 2021/10/27 16:52
 * @email lmay@lmaye.com
 * @since JDK1.8
 */
@Data
@ConfigurationProperties("oauth")
public class OauthProperties {
    /**
     * Cookie生命周期(单位: 秒)
     */
    private Integer cookieMaxAge;

    /**
     * Cookie站点
     */
    private String cookieDomain;

    /**
     * Spring默认授权地址
     */
    private String defaultTokenUrl;

    /**
     * Token有效时间(单位: 秒)
     */
    private Integer expiresIn;
}
