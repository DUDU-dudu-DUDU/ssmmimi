package com.wu.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFiler implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object admin = request.getSession().getAttribute("admin");
        if(admin == null && request.getRequestURI().indexOf("/admin/login.action") == -1){
            // 没有登录
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        }else{
            // 已经登录，继续请求下一级资源（继续访问）
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
