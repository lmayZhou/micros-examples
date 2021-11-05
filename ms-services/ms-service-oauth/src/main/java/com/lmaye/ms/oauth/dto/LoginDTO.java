package com.lmaye.ms.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * -- Login DTO
 *
 * @author lmay.Zhou
 * @date 2020/12/24 10:11
 * @email lmay@lmaye.com
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "LoginDTO", description = "登录参数")
public class LoginDTO implements Serializable {
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
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String verifyCode;
}
