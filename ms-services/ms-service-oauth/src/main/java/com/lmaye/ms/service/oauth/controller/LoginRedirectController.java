package com.lmaye.ms.service.oauth.controller;

import com.lmaye.ms.service.oauth.utils.IdentCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param from  from
     * @param model model
     * @return String
     */
    @RequestMapping("/login")
    public String login(String from, Model model) {
        model.addAttribute("from", from);
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
}
