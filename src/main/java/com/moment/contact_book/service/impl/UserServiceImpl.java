package com.moment.contact_book.service.impl;

import com.moment.contact_book.entity.User;
import com.moment.contact_book.exception.ServiceException;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.UserService;
import com.moment.contact_book.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: UserService实现
 * @Author: Moment
 * @Date: 2020/5/4 17:44
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(String loginName, String password) {
        log.info("login:" + loginName + ":" + password);
        User user = userMapper.findByLoginName(loginName);
        if (user != null) {
            if (user.getUPassword().equals(password)) {
                log.info("login success: userName:" + user.getULoginName());
                return user;
            }
        }
        log.info("login fail");
        throw new ServiceException("用户名或密码错误");
    }

    @Override
    public int register(String loginName, String password, String email) {
        log.info("register:" + loginName + ":" + password + ":" + email);
        if (userMapper.findByLoginName(loginName) != null) {
            log.info("register fail: has the same loginName");
            throw new ServiceException("用户名已存在");
        }
        if (userMapper.findByEmail(email) != null) {
            log.info("register fail: has the same email");
            throw new ServiceException("邮箱已存在");
        }
        User user = new User();
        user.setUId(ServiceUtils.generateShortUuid());
        user.setULoginName(loginName);
        user.setUPassword(password);
        user.setUEmail(email);
        int result = userMapper.insertUser(user);
        if (result == 1) {
            log.info("register success! the info is:" + user);
            return 1;
        }
        log.info("register fail: Unknown reason");
        throw new ServiceException("注册失败: Unknown reason 未知原因");
    }

    @Override
    public int changePassword(String loginName, String oldPassword, String newPassword) {
        log.info("change password: " + loginName);
        User user = userMapper.findByLoginName(loginName);
        if (user == null) {
            log.info("change failed: unknown user");
            throw new ServiceException("用户不存在");
        }
        if (!user.getUPassword().equals(oldPassword)) {
            log.info("change failed: wrong password");
            throw new ServiceException("用户验证不通过,密码错误");
        }
        user.setUPassword(newPassword);
        int result = userMapper.updatePasswordByLoginName(loginName, newPassword);
        if (result == 1) {
            log.info("change password success!");
            return 1;
        }
        log.info("change password fail: Unknown reason");
        throw new ServiceException("密码更改失败: Unknown reason 未知原因");
    }

    @Override
    public User changeInfo(User user) {
        log.info("change info: " + user);
        if (!(user.getULoginName().isEmpty()
                | user.getUPassword().isEmpty()
                | user.getUId().isEmpty()
                | user.getUEmail().isEmpty())) {
            User userOld = login(user.getULoginName(), user.getUPassword());
            if (userOld != null) {
                if (userMapper.updateInfoById(user) == 1) {
                    log.info("change info success!");
                    return user;
                } else {
                    log.info("change info failed: the update process ERRORS");
                    throw new ServiceException("数据库更新信息错误: 请检查你提交的信息");
                }
            } else {
                log.info("change info failed: the login info is wrong");
                throw new ServiceException("用户验证失败");
            }
        }
        log.info("change info failed: some necessary info is empty");
        throw new ServiceException("用户验证信息缺失");
    }

    @Override
    public int deleteUser(String loginName, String password) {
        log.info("delete the user:" + loginName);
        User user = login(loginName, password);
        if (user != null) {
            if (userMapper.deleteUserById(user.getUId()) == 1) {
                log.info("delete success!");
                return 1;
            } else {
                log.info("delete failed: process ERROR");
                throw new ServiceException("数据库删除信息错误: 请检查你提交的信息");
            }
        } else {
            log.info("delete failed: login info is wrong");
            throw new ServiceException("用户验证信息缺失");
        }
    }
}
