package com.lmaye.ms.user.repository;

import com.lmaye.ms.user.api.entity.SysRolePermission;
import com.lmaye.cloud.starter.mybatis.repository.IMyBatisRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色权限 Mapper 接口
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Mapper
public interface SysRolePermissionRepository extends IMyBatisRepository<SysRolePermission> {

}
