package com.lmaye.ms.services.api.user.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型(00. 系统用户; 01. 注册用户;)
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phonenumber;

    /**
     * 性别(0. 男; 1. 女; 2. 未知;)
     */
    private String sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 微博
     */
    private String weibo;

    /**
     * 状态(0. 正常; 1. 停用; 2. 删除;)
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 拓展字段
     */
    private String ext;

    /**
     * 版本号
     */
    private Integer varsion;


}
