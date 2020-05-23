package com.moment.contact_book.controller;

import com.moment.contact_book.entity.User;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description: 测试用controller
 * @Author: Moment
 * @Date: 2020/5/4 22:52
 */

@ApiIgnore
@RestController
public class TestController {


    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @Autowired
    public TestController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RequestMapping("test")
    String test(){
        User login = userService.login("momincong", "momincong");
        if (login != null) {
            return "yes";
        }
        return "test";
    }
}
