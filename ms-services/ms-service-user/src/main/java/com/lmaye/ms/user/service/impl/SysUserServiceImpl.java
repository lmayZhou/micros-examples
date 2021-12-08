package com.lmaye.ms.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmaye.cloud.core.constants.YesOrNo;
import com.lmaye.cloud.starter.mybatis.service.impl.MyBatisServiceImpl;
import com.lmaye.cloud.starter.mybatis.utils.MyBatisUtils;
import com.lmaye.cloud.starter.web.query.ListQuery;
import com.lmaye.cloud.starter.web.query.Sort;
import com.lmaye.ms.user.api.entity.SysUser;
import com.lmaye.ms.user.repository.SysUserRepository;
import com.lmaye.ms.user.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

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
    @Override
    public List<SysUser> queryAssociate(ListQuery query) {
        QueryWrapper<Object> queryWrapper = MyBatisUtils.convert(query.getQuery());
        Sort sort = query.getSort();
        if (!Objects.isNull(sort) && !CollectionUtils.isEmpty(sort.getOrder())) {
            sort.getOrder().forEach(o -> queryWrapper.orderBy(true,
                    Objects.equals(YesOrNo.YES.getCode(), o.getAsc()), o.getName()));
        }
        return super.getBaseMapper().queryAssociate(queryWrapper);
    }
}
