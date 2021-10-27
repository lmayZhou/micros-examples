package com.lmaye.ms.service.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * -- Auth DTO
 * - 授权认证参数
 *
 * @author Lmay Zhou
 * @date 2021/1/5 10:58
 * @email lmay@lmaye.com
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthDTO", description = "授权认证参数")
public class AuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 密码
     */
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 验证码Key
     */
    @ApiModelProperty("验证码Key")
    private String key;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String verifyCode;

    /**
     * 客户端ID，分配给应用的AppId
     */
    @NotEmpty
    @ApiModelProperty(value = "客户端ID，分配给应用的AppId", required = true)
    private String clientId;

    /**
     * 客户端密钥，分配给网站的AppKey
     */
    @NotEmpty
    @ApiModelProperty(value = "客户端密钥，分配给网站的AppKey", required = true)
    private String clientSecret;

    /**
     * 授权范围
     */
    @ApiModelProperty("授权范围")
    private String scope;
}
