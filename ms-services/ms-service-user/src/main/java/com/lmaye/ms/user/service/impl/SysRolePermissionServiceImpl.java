package com.lmaye.ms.user.service.impl;

import com.lmaye.ms.user.repository.SysRolePermissionRepository;
import com.lmaye.ms.user.service.ISysRolePermissionService;
import com.lmaye.ms.services.api.user.entity.SysRolePermission;
import com.lmaye.cloud.starter.mybatis.service.impl.MyBatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Service
public class SysRolePermissionServiceImpl extends MyBatisServiceImpl<SysRolePermissionRepository, SysRolePermission, Long>
        implements ISysRolePermissionService {

}
