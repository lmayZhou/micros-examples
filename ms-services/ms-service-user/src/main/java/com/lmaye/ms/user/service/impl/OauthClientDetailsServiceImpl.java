package com.lmaye.ms.user.service.impl;

import com.lmaye.cloud.starter.mybatis.service.impl.MyBatisServiceImpl;
import com.lmaye.ms.user.repository.OauthClientDetailsRepository;
import com.lmaye.ms.user.service.IOauthClientDetailsService;
import com.lmaye.ms.service.user.api.entity.OauthClientDetails;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户端应用注册信息 服务实现类
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Service
public class OauthClientDetailsServiceImpl extends MyBatisServiceImpl<OauthClientDetailsRepository, OauthClientDetails, Long>
        implements IOauthClientDetailsService {

}
