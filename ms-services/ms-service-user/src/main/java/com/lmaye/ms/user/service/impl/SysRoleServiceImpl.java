package com.lmaye.ms.user.service.impl;

import com.lmaye.ms.user.repository.SysRoleRepository;
import com.lmaye.ms.user.service.ISysRoleService;
import com.lmaye.ms.services.api.user.entity.SysRole;
import com.lmaye.cloud.starter.mybatis.service.impl.MyBatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Service
public class SysRoleServiceImpl extends MyBatisServiceImpl<SysRoleRepository, SysRole, Long> implements ISysRoleService {

}
