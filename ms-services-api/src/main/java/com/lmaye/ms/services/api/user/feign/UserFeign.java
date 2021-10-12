package com.lmaye.ms.services.api.user.feign;

import com.lmaye.cloud.starter.web.context.ResultVO;
import com.lmaye.ms.services.api.user.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * -- User Feign
 *
 * @author Lmay Zhou
 * @date 2020/12/25 12:10
 * @email lmay@lmaye.com
 */
@FeignClient(name="user-service")
@RequestMapping("/user")
public interface UserFeign {
    /**
     * 获取用户信息
     * - 根据用户名
     *
     * @param username 用户名
     * @return ResultVO<SysUser>
     */
    @GetMapping("/queryByUserName/{username}")
    ResultVO<SysUser> queryByUserName(@PathVariable String username);
}
