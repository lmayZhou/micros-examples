package com.lmaye.ms.service.user.controller;

import com.lmaye.ms.core.context.ResultVO;
import com.lmaye.ms.core.query.Query;
import com.lmaye.ms.core.query.TermQuery;
import com.lmaye.ms.service.user.service.ISysUserService;
import com.lmaye.ms.services.api.user.entity.SysUser;
import com.lmaye.ms.starter.mybatis.utils.MyBatisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * -- 用户 Controller
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/5 13:22 星期日
 */
@RestController
//@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {
    /**
     * Sys User Service
     */
    private final ISysUserService sysUserService;

    public UserController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 获取用户信息
     * - 根据用户名
     *
     * @param username 用户名
     * @return Mono<ResultVO<SysUser>>
     */
    @GetMapping("/queryByUserName/{username}")
//    @PreAuthorize(value = "hasAuthority('admin')")
    @ApiOperation(value = "根据用户名获取用户信息", notes = "根据用户名获取用户信息", response = ResultVO.class)
    public Mono<ResultVO<SysUser>> queryByUserName(@PathVariable String username) {
        Query query = new Query();
        // 用户名
        TermQuery usernameTerm = new TermQuery();
        usernameTerm.setField("user_name");
        usernameTerm.setValue(username);
        query.setTerms(Collections.singletonList(usernameTerm));
        // 手机号
        Query mobileQuery = new Query();
        TermQuery mobileTerm = new TermQuery();
        mobileTerm.setField("phonenumber");
        mobileTerm.setValue(username);
        mobileQuery.setTerms(Collections.singletonList(mobileTerm));
        // 邮箱
        Query emailQuery = new Query();
        TermQuery emailTerm = new TermQuery();
        emailTerm.setField("email");
        emailTerm.setValue(username);
        emailQuery.setTerms(Collections.singletonList(emailTerm));
        mobileQuery.setShould(emailQuery);
        query.setShould(mobileQuery);
        return Mono.just(ResultVO.success(sysUserService.getOne(MyBatisUtils.convert(query))));
    }
}
