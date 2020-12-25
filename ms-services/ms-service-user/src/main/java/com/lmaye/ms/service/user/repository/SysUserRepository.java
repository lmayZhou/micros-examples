package com.lmaye.ms.service.user.repository;

import com.lmaye.ms.services.api.user.entity.SysUser;
import com.lmaye.ms.starter.mybatis.repository.IMyBatisRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Mapper
public interface SysUserRepository extends IMyBatisRepository<SysUser> {

}
