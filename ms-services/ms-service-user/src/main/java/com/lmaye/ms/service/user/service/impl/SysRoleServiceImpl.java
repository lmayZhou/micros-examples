package com.lmaye.ms.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmaye.ms.service.user.mapper.SysRoleMapper;
import com.lmaye.ms.service.user.service.ISysRoleService;
import com.lmaye.ms.services.api.user.entity.SysRole;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

}
