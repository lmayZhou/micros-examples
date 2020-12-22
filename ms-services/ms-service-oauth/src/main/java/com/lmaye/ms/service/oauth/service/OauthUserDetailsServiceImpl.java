package com.lmaye.ms.service.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        List<User> users = new ArrayList<>();
        users.add(new User("lmay", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        users.add(new User("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        users.add(new User("mark", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        List<User> findUserList = users.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
