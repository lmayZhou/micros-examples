package com.lmaye.ms.user.service.impl;

import com.lmaye.ms.user.repository.SysUserRoleRepository;
import com.lmaye.ms.user.service.ISysUserRoleService;
import com.lmaye.ms.service.user.api.entity.SysUserRole;
import com.lmaye.cloud.starter.mybatis.service.impl.MyBatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Service
public class SysUserRoleServiceImpl extends MyBatisServiceImpl<SysUserRoleRepository, SysUserRole, Long>
        implements ISysUserRoleService {

}
