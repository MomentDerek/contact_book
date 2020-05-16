package com.moment.contact_book.controller;

import com.alibaba.fastjson.JSONObject;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            returnMsg.put("msg", "success");
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

}
