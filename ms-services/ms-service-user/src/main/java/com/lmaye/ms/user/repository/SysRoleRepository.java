package com.lmaye.ms.user.repository;

import com.lmaye.ms.services.api.user.entity.SysRole;
import com.lmaye.cloud.starter.mybatis.repository.IMyBatisRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息 Mapper 接口
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Mapper
public interface SysRoleRepository extends IMyBatisRepository<SysRole> {

}
