package com.lmaye.ms.service.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;

/**
 * -- Authorization Server Config
 * - 认证服务器配置
 *
 * @author lmay.Zhou
 * @date 2020/12/22 17:18
 * @email lmay@lmaye.com
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 授权认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * SpringSecurity 用户自定义授权认证类
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 密钥的配置
     */
    @Resource(name = "keyProperties")
    private KeyProperties keyProperties;

    /**
     * 更改存储token的策略，默认是内存策略,修改为jwt
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        //基于session认证
        //return new JdbcTokenStore(dataSource);
        //基于token认证
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 读取密钥的配置
     *
     * @return KeyProperties
     */
    @Bean("keyProperties")
    public KeyProperties keyProperties(){
        return new KeyProperties();
    }

    /**
     * JWT令牌转换器
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // jwt使用这个key来签名，验证token的服务也使用这个key来验签
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(keyProperties.getKeyStore().getLocation(),
                keyProperties.getKeyStore().getSecret().toCharArray())
                .getKeyPair(keyProperties.getKeyStore().getAlias(), keyProperties.getKeyStore().getPassword().toCharArray());
        converter.setKeyPair(keyPair);
        return converter;
    }

    /**
     * 添加自定义token增强器实现颁发额外信息的token,因为默认颁发token的字段只有username和ROLE
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer customTokenEnhancer() {
        // 自定义实现
        return new CustomTokenEnhancer();
    }

    /**
     * 1. 配置数据源
     *
     * @param clients ClientDetailsServiceConfigurer
     * @throws Exception 异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置数据源
        clients.jdbc(dataSource);
        /*clients.inMemory()
                // 配置client_id
                .withClient("admin")
                // 配置client_secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问token的有效期
                .accessTokenValiditySeconds(3600)
                // 配置刷新token的有效期
                .refreshTokenValiditySeconds(3600)
                // 配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://www.baidu.com")
                // 配置申请的权限范围
                .scopes("all")
                // 配置grant_type，表示授权类型
                .authorizedGrantTypes("authorization_code", "password");*/
    }

    /**
     * 2.认证服务器安全配置
     *
     * @param security AuthorizationServerSecurityConfigurer
     * @throws Exception 异常
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //基于session认证会用到
        /*security.checkTokenAccess("isAuthenticated()")
                // 认证中心往外面暴露的一个用来获取jwt的SigningKey的服务/oauth/token_key,但我选择在每个资源服务器本地配置SigningKey
                .tokenKeyAccess("isAuthenticated()");*/
        security.allowFormAuthenticationForClients().passwordEncoder(new BCryptPasswordEncoder())
                .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    /**
     * 3. 配置customTokenEnhancer,自定义UserDetailService,token存储策略
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @throws Exception 异常
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 将增强的token设置到增强链中
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtAccessTokenConverter()));
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService).tokenEnhancer(chain);
    }
}
