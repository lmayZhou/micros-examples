package com.lmaye.ms.service.user.service.impl;

import com.lmaye.ms.service.user.repository.SysUserRepository;
import com.lmaye.ms.service.user.service.ISysUserService;
import com.lmaye.ms.services.api.user.entity.SysUser;
import com.lmaye.ms.starter.mybatis.service.impl.MyBatisServiceImpl;
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
public class SysUserServiceImpl extends MyBatisServiceImpl<SysUserRepository, SysUser, Long> implements ISysUserService {

}
