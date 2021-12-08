package com.lmaye.ms.user.service;

import com.lmaye.cloud.starter.mybatis.service.IMyBatisService;
import com.lmaye.cloud.starter.web.query.ListQuery;
import com.lmaye.ms.user.api.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
public interface ISysUserService extends IMyBatisService<SysUser, Long> {
    List<SysUser> queryAssociate(ListQuery query);
}
