package com.lmaye.ms.services.api.user.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限Code
     */
    private String roleKey;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 范围(1. 全部数据; 2. 自定义数据; 3. 本部门数据; 4. 本部门及以下数据;)
     */
    private Boolean scope;

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
