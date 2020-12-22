package com.lmaye.ms.service.oauth.controller;

import com.lmaye.ms.core.context.ResultVO;
import com.lmaye.ms.service.oauth.entity.User;
import com.lmaye.ms.service.oauth.utils.IdentCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * --
 *
 * @author lmay.Zhou
 * @date 2020/12/22 14:54
 * @email lmay@lmaye.com
 */
public class SecurityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/log")
    public String login(HttpSession session, Model model, String username, String type) {
        // 错误信息
        String errorMsg = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (StringUtils.isNotBlank(errorMsg)) {
            model.addAttribute("errorMsg", errorMsg);
        }
        // 用户名
        if (StringUtils.isNotBlank(username)) {
            model.addAttribute("username", username);
            User user = new User();
            user.setUsername("lmay");
            user.setPassword("123456");
            user.setTimes(3);
            // 密码错误n次, 开启验证码
            if (user.getTimes() >= 3) {
                model.addAttribute("enableCaptcha", true);
            }
        }
        // 登录类型
        type = StringUtils.defaultIfBlank(type, "account");
        model.addAttribute("type", type);
        return "login";
    }

    @GetMapping("/public/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        IdentCode identifyingCode = new IdentCode();
        try {
            identifyingCode.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调换到注册或绑定三方账号页面
     */
    @GetMapping("/signup")
    public String signup() {
        return "register-or-bind";
    }

    /**
     * 调换到注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/user/self")
    @ResponseBody
    public ResultVO<User> self() {
        User user = new User();
        user.setUsername("lmay");
        user.setPassword("123456");
        user.setTimes(3);
        return ResultVO.success(user);
    }

}
