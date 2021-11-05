package com.lmaye.ms.user.repository;

import com.lmaye.ms.user.api.entity.OauthClientDetails;
import com.lmaye.cloud.starter.mybatis.repository.IMyBatisRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户端应用注册信息 Mapper 接口
 * </p>
 *
 * @author Lmay Zhou
 * @since 2020-12-25
 */
@Mapper
public interface OauthClientDetailsRepository extends IMyBatisRepository<OauthClientDetails> {

}
