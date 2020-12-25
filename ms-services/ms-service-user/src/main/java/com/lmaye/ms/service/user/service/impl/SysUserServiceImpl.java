package com.lmaye.ms.service.user.service.impl;

import com.lmaye.ms.services.api.user.entity.SysUser;
import com.lmaye.ms.service.user.mapper.SysUserMapper;
import com.lmaye.ms.service.user.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
