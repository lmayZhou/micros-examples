package com.lmaye.ms.user.repository;

import com.lmaye.cloud.starter.mybatis.repository.IMyBatisRepository;
import com.lmaye.ms.user.api.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Mapper
public interface SysUserRoleRepository extends IMyBatisRepository<SysUserRole> {

}
