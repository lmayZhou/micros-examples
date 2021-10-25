package com.lmaye.ms.service.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户端应用的账号
     */
    @TableId("client_id")
    private String clientId;

    /**
     * 客户端应用可访问的资源服务器列表
     */
    @TableField("resource_ids")
    private String resourceIds;

    /**
     * 客户端应用的密码
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 资源服务器拥有的所有权限列表 (get add delete update)
     */
    @TableField("scope")
    private String scope;

    /**
     * 客户端支持的授权码模式列表
     */
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 授权码模式,申请授权码后重定向的uri
     */
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @TableField("authorities")
    private String authorities;

    /**
     * 设置颁发token的有效期
     */
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 颁发refresh_token的有效期
     */
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField("additional_information")
    private String additionalInformation;

    @TableField("autoapprove")
    private String autoapprove;
}
