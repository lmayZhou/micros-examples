package com.lmaye.ms.service.oauth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * --
 *
 * @author lmay.Zhou
 * @date 2020/12/22 18:51
 * @email lmay@lmaye.com
 */
@Data
public class UserTokenVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nickname;

    private String avatar;

    private String description;
}
