package com.lmaye.ms.service.oauth.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * --
 *
 * @author lmay.Zhou
 * @date 2020/12/22 18:51
 * @email lmay@lmaye.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserToken extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nickname;

    private String avatar;

    private String description;

    public UserToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserToken(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, String nickname, String avatar, String description) {
        super(username, password, authorities);
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.description = description;
    }
}
