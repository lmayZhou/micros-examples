package com.lmaye.ms.service.oauth.controller;

import com.lmaye.ms.service.oauth.utils.IdentCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * -- Login Redirect Controller
 *
 * @author lmay.Zhou
 * @date 2020/12/23 14:30
 * @email lmay@lmaye.com
 */
@Controller
@RequestMapping("/oauth")
public class LoginRedirectController {
    /**
     * 登录页面
     *
     * @param session  session
     * @param model    model
     * @param username username
     * @param type     type
     * @return String
     */
    @RequestMapping("/login")
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
            // TODO 错误3次，开启验证码
            if (true) {
                model.addAttribute("enableCaptcha", true);
            }
        }
        // 登录类型
        model.addAttribute("type", StringUtils.defaultIfBlank(type, "account"));
        return "login";
    }

    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        IdentCode identifyingCode = new IdentCode();
        try {
            identifyingCode.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
