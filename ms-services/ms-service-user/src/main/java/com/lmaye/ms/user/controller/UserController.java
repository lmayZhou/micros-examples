package com.lmaye.ms.user.controller;

import com.lmaye.cloud.starter.mybatis.utils.MyBatisUtils;
import com.lmaye.cloud.starter.web.context.PageResult;
import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.cloud.starter.web.query.ListQuery;
import com.lmaye.cloud.starter.web.query.PageQuery;
import com.lmaye.cloud.starter.web.query.Query;
import com.lmaye.cloud.starter.web.query.TermQuery;
import com.lmaye.ms.user.api.entity.SysUser;
import com.lmaye.ms.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * -- 用户 Controller
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/5 13:22 星期日
 */
@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {
    /**
     * Sys User Service
     */
    private final ISysUserService sysUserService;

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

    /**
     * 新增用户信息
     *
     * @param user 请求参数
     * @return Mono<ResultVO<SysUser>>
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增用户信息", notes = "新增用户信息", response = ResultVO.class)
    public Mono<ResultVO<SysUser>> save(@RequestBody SysUser user) {
        return Mono.just(ResultVO.success(sysUserService.insert(user).orElse(null)));
    }

    /**
     * 关联查询
     *
     * @param query 请求参数
     * @return Mono<ResultVO<List<SysUser>>>
     */
    @PostMapping("/queryAssociate")
    @ApiOperation(value = "关联查询", response = ResultVO.class)
    public Mono<ResultVO<List<SysUser>>> queryAssociate(@RequestBody ListQuery query) {
        return Mono.just(ResultVO.success(sysUserService.queryAssociate(query)));
    }
}
