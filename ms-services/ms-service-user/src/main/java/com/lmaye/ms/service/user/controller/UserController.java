package com.lmaye.ms.service.user.controller;

import com.lmaye.ms.core.context.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * -- 用户 Controller
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/5 13:22 星期日
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {
    /**
     * 测试接口
     *
     * @return Mono<ResponseResult<String>>
     */
    @GetMapping("/query")
//    @PreAuthorize(value = "hasAuthority('admin')")
    @ApiOperation(value = "测试接口", notes = "测试示例", response = ResultVO.class)
    public Mono<ResultVO<String>> test() {
        return Mono.just(ResultVO.success("Test"));
    }
}
