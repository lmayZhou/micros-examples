package com.lmaye.ms.user.repository;

import com.lmaye.ms.user.api.entity.SysUser;
import com.lmaye.cloud.starter.mybatis.repository.IMyBatisRepository;
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
