package com.moment.contact_book.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @Description: 用户Controller层
 * @Author: Moment
 * @Date: 2020/5/15 22:04
 */

@RestController
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(
            @NotNull(message = "用户名不能为空") String loginName,
            @NotNull(message = "密码不能为空") String password,
            @NotNull(message = "邮箱不能为空") @Email(message = "邮箱格式错误") String email) {
        int register = userService.register(loginName, password, email);
        JSONObject returnMsg = new JSONObject();
        if (register == 1) {
            returnMsg.put("status", "200");
            returnMsg.put("message", "success");
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/user/login")
    public String login(
            @NotNull(message = "用户名不能为空") String loginName,
            @NotNull(message = "密码不能为空") String password) {
        User user = userService.login(loginName, password);
        //转化为json对象并移除密码(主要是因为user对象设置了校验,密码不能为空)
        JSONObject userJSON = (JSONObject) JSON.toJSON(user);
        userJSON.remove("uPassword");

        JSONObject returnMsg = new JSONObject();
        if (null!=user) {
            returnMsg.put("status", "200");
            returnMsg.put("message", userJSON);
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/user/changePassword")
    public String changePassword(
            @NotNull(message = "用户名不能为空") String loginName,
            @NotNull(message = "旧密码不能为空") String oldPassword,
            @NotNull(message = "新密码不能为空") String newPassword) {
        int result = userService.changePassword(loginName, oldPassword, newPassword);
        JSONObject returnMsg = new JSONObject();
        if (1==result) {
            returnMsg.put("status", "200");
            returnMsg.put("message","success");
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/user/update")
    public String login(@Valid @RequestBody User user) {
        userService.changeInfo(user);
        //转化为json对象并移除密码(主要是因为user对象设置了校验,密码不能为空)
        JSONObject userJSON = (JSONObject) JSON.toJSON(user);
        userJSON.remove("uPassword");

        JSONObject returnMsg = new JSONObject();
        if (null!=user) {
            returnMsg.put("status", "200");
            returnMsg.put("message", userJSON);
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/user/delete")
    public String register(
            @NotNull(message = "用户名不能为空") String loginName,
            @NotNull(message = "密码不能为空") String password) {
        int result = userService.deleteUser(loginName, password);
        JSONObject returnMsg = new JSONObject();
        if (result == 1) {
            returnMsg.put("status", "200");
            returnMsg.put("message", "success");
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }
}
