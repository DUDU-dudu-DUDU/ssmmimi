package com.wu.service.impl;

import com.wu.mapper.AdminMapper;
import com.wu.pojo.Admin;
import com.wu.pojo.AdminExample;
import com.wu.service.AdminService;
import com.wu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        // 根据传入的对象 或db查询相应哦用户对象
        // 如果有条件  则创建AdminExample对象  用来追加条件
        AdminExample example = new AdminExample();
        /**如何添加条件
         * select * from admin where a_name = 'admin'
         *
          */
        // 添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list.size() > 0){
            Admin admin = list.get(0);
            // 查询到用户对象  进行比较  密文比较
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
