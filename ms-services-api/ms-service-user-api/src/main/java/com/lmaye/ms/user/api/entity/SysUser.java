package com.lmaye.ms.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 客户端ID
     */
    @TableField("client_id")
    private String clientId;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户类型(00. 系统用户; 01. 注册用户;)
     */
    @TableField("user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phonenumber")
    private String phonenumber;

    /**
     * 性别(0. 男; 1. 女; 2. 未知;)
     */
    @TableField("sex")
    private String sex;

    /**
     * 头像路径
     */
    @TableField("avatar")
    private String avatar;

    /**
     * QQ
     */
    @TableField("qq")
    private String qq;

    /**
     * 微信
     */
    @TableField("wechat")
    private String wechat;

    /**
     * 微博
     */
    @TableField("weibo")
    private String weibo;

    /**
     * 状态(0. 正常; 1. 停用; 2. 删除;)
     */
    @TableField("status")
    private Boolean status;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField("update_at")
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 拓展字段
     */
    @TableField("ext")
    private String ext;

    /**
     * 版本号
     */
    @Version
    @TableField("version")
    private Integer version;
}
