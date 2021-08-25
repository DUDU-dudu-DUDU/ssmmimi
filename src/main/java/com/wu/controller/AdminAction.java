package com.wu.controller;

import com.wu.pojo.Admin;
import com.wu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminAction {

    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request,HttpSession session){
        Admin admin = adminService.login(name, pwd);
        if (admin != null){
            // 登录成功
            session.setAttribute("admin", admin);
            request.setAttribute("admin", admin);
            return "main";
        }else {
            // 登录失败
            request.setAttribute("errmsg", "用户名或者密码错误");
            return "login";
        }
    }


}
