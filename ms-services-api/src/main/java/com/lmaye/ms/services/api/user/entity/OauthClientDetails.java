package com.lmaye.ms.services.api.user.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户端应用注册信息
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户端应用的账号
     */
    private String clientId;

    /**
     * 客户端应用可访问的资源服务器列表
     */
    private String resourceIds;

    /**
     * 客户端应用的密码
     */
    private String clientSecret;

    /**
     * 资源服务器拥有的所有权限列表 (get add delete update)
     */
    private String scope;

    /**
     * 客户端支持的授权码模式列表
     */
    private String authorizedGrantTypes;

    /**
     * 授权码模式,申请授权码后重定向的uri
     */
    private String webServerRedirectUri;

    /**
     * 权限
     */
    private String authorities;

    /**
     * 设置颁发token的有效期
     */
    private Integer accessTokenValidity;

    /**
     * 颁发refresh_token的有效期
     */
    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;


}
