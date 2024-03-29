package com.lmaye.ms.user.service;

import com.lmaye.ms.user.api.entity.OauthClientDetails;
import com.lmaye.cloud.starter.mybatis.service.IMyBatisService;

/**
 * <p>
 * 客户端应用注册信息 服务类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
public interface IOauthClientDetailsService extends IMyBatisService<OauthClientDetails, Long> {

}
