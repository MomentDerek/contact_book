package com.moment.contact_book.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.UserService;
import io.swagger.annotations.*;
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

@Api(value="用户controller",tags={"用户操作接口"})
@RestController
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ApiOperation("注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "email", value = "邮箱")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为success",example = "success")
    })
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

    @ApiOperation("登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为用户信息",dataTypeClass = com.moment.contact_book.entity.User.class)
    })
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

    @ApiOperation("更改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名"),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码"),
            @ApiImplicitParam(name = "newPassword", value = "新密码")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为success",example = "success")
    })
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

    @ApiOperation(value = "用户信息更新接口",notes = "这里的用户名和密码不能错,不然无法更新，部分值可为空，具体请看其他文档-实体类说明")
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为用户信息",dataTypeClass = com.moment.contact_book.entity.User.class)
    })
    @PostMapping("/user/update")
    public String update(@Valid @RequestBody User user) {
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


    @ApiOperation(value = "删除用户接口",notes = "这里的用户名和密码不能错,不然无法删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为success",example = "success")
    })
    @PostMapping("/user/delete")
    public String delete(
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
