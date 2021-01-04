package com.lmaye.ms.services.api.user.entity;

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
 * 角色信息
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色权限Code
     */
    @TableField("role_key")
    private String roleKey;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 范围(1. 全部数据; 2. 自定义数据; 3. 本部门数据; 4. 本部门及以下数据;)
     */
    @TableField("scope")
    private Boolean scope;

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
