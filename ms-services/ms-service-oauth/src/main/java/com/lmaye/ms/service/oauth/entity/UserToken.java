package com.lmaye.ms.service.oauth.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * -- User Token
 * - 增强Token
 *
 * @author lmay.Zhou
 * @date 2020/12/22 18:51
 * @email lmay@lmaye.com
 */
@ApiModel(value = "UserToken", description = "用户Token")
public class UserToken extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    public UserToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserToken(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, String nickname) {
        super(username, password, authorities);
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
