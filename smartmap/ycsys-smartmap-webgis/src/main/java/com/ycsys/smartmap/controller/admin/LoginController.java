package com.ycsys.smartmap.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2016/10/25.
 */
@Controller
public class LoginController {
    @RequestMapping(value="login",method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()||subject.isRemembered()){
            return "redirect:/index";
        }
        return "/login";
    }
    /**
     * 登录失败
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping(value="login",method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        return "/login";
    }

    /**
     * 登出
     * @param model
     * @return
     */
    @RequestMapping(value="logout")
    public String logout(Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }
}
