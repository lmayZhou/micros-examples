package com.lmaye.ms.oauth.config;

import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.oauth.entity.UserToken;
import com.lmaye.ms.user.api.entity.SysUser;
import com.lmaye.ms.user.api.UserFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * -- Oauth User Details Service Impl
 *
 * @author lmay.Zhou
 * @date 2020/12/22 17:14
 * @email lmay@lmaye.com
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Client Details Service
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * User Feign
     */
    @Autowired
    private UserFeign userFeign;

    /**
     * 加载用户
     * - 根据用户名
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用HttpBasic认证，HttpBasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (Objects.isNull(authentication)) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (!Objects.isNull(clientDetails)) {
                return new User(username, clientDetails.getClientSecret(), clientDetails.getAuthorities());
            }
        }
        // 数据查询
        ResultVO<SysUser> result = userFeign.queryByUserName(username);
        SysUser user = result.getData();
        if(Objects.isNull(user)) {
            return null;
        }
        return new UserToken(user.getUserName(), user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"), user.getId(), "");
    }
}
