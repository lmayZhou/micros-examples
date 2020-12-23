package com.lmaye.ms.service.oauth.service;

import com.lmaye.ms.service.oauth.entity.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * -- Oauth User Details Service Impl
 *
 * @author lmay.Zhou
 * @date 2020/12/22 17:14
 * @email lmay@lmaye.com
 */
@Service
public class OauthUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, clientDetails.getAuthorities());
            }
        }
        // TODO 查数据库
        String password = passwordEncoder.encode("123456");
        List<UserToken> users = new ArrayList<>();
        users.add(new UserToken("lmay", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"), 10000L, "Lmay Zhou", "", "1"));
        users.add(new UserToken("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client"), 10001L, "Andy", "", "2"));
        users.add(new UserToken("mark", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client"), 10002L, "Mark", "", "3"));
        List<User> userList = users.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
