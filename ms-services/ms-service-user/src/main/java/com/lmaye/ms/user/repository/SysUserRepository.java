package com.lmaye.ms.user.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lmaye.cloud.starter.mybatis.repository.IMyBatisRepository;
import com.lmaye.ms.user.api.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    /**
     * 关联查询
     *
     * @param query 查询条件
     * @return List<SysUser>
     */
    List<SysUser> queryAssociate(@Param(Constants.WRAPPER) QueryWrapper<?> query);
}
