package com.moment.contact_book.controller;

import com.alibaba.fastjson.JSONObject;
import com.moment.contact_book.entity.ContactType;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description: 联系人类型Controller层
 * @Author: Moment
 * @Date: 2020/5/16 12:37
 */
@RestController
@Validated
@RequestMapping("/user/type")
public class ContactTypeController {

    private final TypeService typeService;

    @Autowired
    public ContactTypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping("/all")
    public String allType(@NotNull(message = "用户id不能为空") String userId) {
        List<ContactType> contactTypes = typeService.allType(userId);
        JSONObject returnMsg = new JSONObject();
        if (null != contactTypes) {
            returnMsg.put("status", "200");
            returnMsg.put("message",contactTypes);
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/select")
    public String selectType(@NotNull(message = "用户id不能为空") String  userId,
                             @NotNull(message = "类型id不能为空") String  typeId) {
        ContactType type = typeService.selectType(userId, typeId);
        JSONObject returnMsg = new JSONObject();
        if (null != type) {
            returnMsg.put("status", "200");
            returnMsg.put("message",type);
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/update")
    public String updateType(@Valid @RequestBody ContactType contactType) {
        ContactType type = typeService.changeType(contactType);
        JSONObject returnMsg = new JSONObject();
        if (null != type) {
            returnMsg.put("status", "200");
            returnMsg.put("message",type);
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/add")
    public String addType(@NotNull(message = "用户id不能为空") String  userId,
                          @NotNull(message = "用户id不能为空") String  typeName) {
        ContactType type = typeService.addType(userId,typeName);
        JSONObject returnMsg = new JSONObject();
        if (null != type) {
            returnMsg.put("status", "200");
            returnMsg.put("message",type);
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }

    @PostMapping("/delete")
    public String deleteType(@Valid @RequestBody ContactType contactType) {
        int result = typeService.deleteType(contactType);
        JSONObject returnMsg = new JSONObject();
        if (result == 1) {
            returnMsg.put("status", "200");
            returnMsg.put("message","success");
        } else {
            throw new ControllerException("UnKnown Error 未知错误");
        }
        return returnMsg.toJSONString();
    }
}
