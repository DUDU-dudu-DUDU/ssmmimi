package com.wu.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录成功应该有request
        Object admin = request.getSession().getAttribute("admin");
        System.out.println(admin);
        if (admin == null){
            request.setAttribute("msg", "没有权限，请先登录！");
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
            return false;
        }else {
            return true;
        }
    }
}
