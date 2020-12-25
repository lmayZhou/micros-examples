package com.lmaye.ms.service.user.service.impl;

import com.lmaye.ms.services.api.user.entity.OauthClientDetails;
import com.lmaye.ms.service.user.mapper.OauthClientDetailsMapper;
import com.lmaye.ms.service.user.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

}
