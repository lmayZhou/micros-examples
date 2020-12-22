package com.lmaye.ms.service.oauth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * --
 *
 * @author lmay.Zhou
 * @date 2020/12/22 16:41
 * @email lmay@lmaye.com
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String mobile;

    private int times;
}
