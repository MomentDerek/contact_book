package com.moment.contact_book.service;

import com.moment.contact_book.entity.User;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * 用户服务层
 * 实现：
 * 用户登录，
 * 用户注册，
 * 用户修改密码，
 * 用户修改个人信息，
 * 用户注销
 * @Author: Moment
 * @Date: 2020/5/4 15:36
 */

public interface UserService {

    // 用户登录，用户信息获取
    User login(String loginName, String password);

    // 用户注册，1为成功，2为用户名已存在，3为邮箱已存在，0为未知原因失败
    int register(String loginName, String password, String email);

    // 用户修改密码，1为成功，2为旧密码错误，0为其他原因失败（比如用户名错误）
    int changePassword(String loginName, String oldPassword, String newPassword);

    // 用户修改个人信息
    User changeInfo(User user);

    // 用户注销（1为成功，0为删除失败，2为登录信息错误）
    int deleteUser(String loginName, String password);

}
