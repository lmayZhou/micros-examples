package com.lmaye.ms.service.user.repository;

import com.lmaye.ms.services.api.user.entity.SysRolePermission;
import com.lmaye.ms.starter.mybatis.repository.IMyBatisRepository;
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
