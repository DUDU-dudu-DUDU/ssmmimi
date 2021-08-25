package com.wu.service;

import com.wu.pojo.Admin;

public interface AdminService {
    // 完成登录判断
    Admin login(String name, String pw);
}
