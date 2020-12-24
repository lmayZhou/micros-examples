package com.lmaye.ms.service.oauth.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * -- Auth Token
 *
 * @author lmay.Zhou
 * @date 2020/12/22 18:51
 * @email lmay@lmaye.com
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthToken", description = "认证Token")
public class AuthToken implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 令牌信息
     */
    @ApiModelProperty("令牌信息")
    private String accessToken;

    /**
     * 刷新token
     */
    @ApiModelProperty("刷新Token")
    private String refreshToken;

    /**
     * JWT短令牌
     */
    @ApiModelProperty("JWT短令牌")
    private String jti;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private Integer expiresIn;

    /**
     * Token类型
     */
    @ApiModelProperty("Token类型")
    private String tokenType;

    /**
     * 启用验证码
     */
    @ApiModelProperty("启用验证码")
    private Boolean verifyEnable;
}