package com.moment.contact_book.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.moment.contact_book.entity.ContactType;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.TypeService;
import io.swagger.annotations.*;
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
@Api(value="联系人类型controller",tags={"联系人类型操作接口"})
@RestController
@Validated
@RequestMapping("/user/type")
public class ContactTypeController {

    private final TypeService typeService;

    @Autowired
    public ContactTypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @ApiOperation("获取类型列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id",example = "000001")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时返回类型列表",example = "这玩意写不出来,自己试试就知道了")
    })
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

    @ApiOperation("获取类型接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeId", value = "类型id"),
            @ApiImplicitParam(name = "userId", value = "用户id")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时返回类型信息",dataTypeClass = com.moment.contact_book.entity.ContactType.class)
    })
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

    @ApiOperation("更新类型信息接口")
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时返回类型信息",dataTypeClass = com.moment.contact_book.entity.ContactType.class)
    })
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

    @ApiOperation("新增类型接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeName", value = "类型名称"),
            @ApiImplicitParam(name = "userId", value = "用户id")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时返回类型信息",dataTypeClass = com.moment.contact_book.entity.ContactType.class)
    })
    @PostMapping("/add")
    public String addType(@NotNull(message = "用户id不能为空") String  userId,
                          @NotNull(message = "类型名称不能为空") String  typeName) {
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

    @ApiOperation("删除类型接口")
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为success",example = "success")
    })
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
