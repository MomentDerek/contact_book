package com.moment.contact_book.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.moment.contact_book.entity.ContactBook;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.ContactService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 联系人的Controller层
 * @Author: Moment
 * @Date: 2020/5/16 13:15
 */
@Api(value="联系人controller",tags={"联系人操作接口"})
@RestController
@Validated
@RequestMapping("/user/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @ApiOperation(value = "查询所有联系人接口",notes = "这里的用户id和用户名是二选一的,两个都有的时候是以id为主，info参数请忽略（随便填，bug导致该选项出现）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UId",value = "用户id"),
            @ApiImplicitParam(name = "ULoginName",value = "用户名")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为联系人列表",example = "这里是一个联系人的数组,你用一下就知道了")
    })
    @PostMapping("/all")
    public String listAll(@NotNull(message = "查询信息不得为空") @RequestParam Map<String, String> info) {
        JSONObject returnMsg = new JSONObject();
        if (info.containsKey("UId")) {
            List<ContactBook> contactBooks = contactService.listByUId(info.get("UId"));
            returnMsg.put("status", "200");
            returnMsg.put("message", contactBooks);
            return returnMsg.toJSONString();
        } else if (info.containsKey("ULoginName")) {
            List<ContactBook> contactBooks = contactService.listByULoginName(info.get("ULoginName"));
            returnMsg.put("status", "200");
            returnMsg.put("message", contactBooks);
            return returnMsg.toJSONString();
        } else {
            throw new ControllerException("查询信息错误，请检查查询字段", 400);
        }
    }

    @ApiOperation("查询指定联系人接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UId", value = "用户id"),
            @ApiImplicitParam(name = "CId", value = "联系人id")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为联系人信息",dataTypeClass = com.moment.contact_book.entity.ContactBook.class)
    })
    @PostMapping("/select")
    public String selectContact(@NotNull(message = "用户id不得为空") String UId,
                                @NotNull(message = "联系人id不得为空") String CId) {
        JSONObject returnMsg = new JSONObject();
        ContactBook contactBook = contactService.listByUIdAndCId(UId, CId);
        if (contactBook == null) {
            throw new ControllerException("查询特定联系人错误：未知错误");
        }
        returnMsg.put("status", "200");
        returnMsg.put("message", contactBook);
        return returnMsg.toJSONString();
    }

    @ApiOperation(value = "按照规则查询联系人接口",notes = "同样请忽略info（随便填，bug导致该选项出现），UId为必须，其他的可选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UId",value = "用户id",required = true),
            @ApiImplicitParam(name = "CName",value = "联系人名称,该选项支持模糊查询"),
            @ApiImplicitParam(name = "CSex",value = "联系人性别"),
            @ApiImplicitParam(name = "CType",value = "联系人类别id (这里建议你从类别接口那边获取,给个列表给用户选)")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为联系人列表",example = "这里是一个联系人的数组,你用一下就知道了")
    })
    @PostMapping("/search")
    public String searchContact(@NotNull(message = "查询信息不得为空") @RequestParam Map<String, String> info) {
        JSONObject returnMsg = new JSONObject();
        if (!info.containsKey("UId")) {
            throw new ControllerException("缺少用户id字段，请检查", 400);
        }
        String uId = info.get("UId");
        String cName = "";
        String cSex = "";
        String cType = "";
        if (info.containsKey("CName")) {
            cName = info.get("CName");
        }
        if (info.containsKey("CSex")) {
            cSex = info.get("CSex");
        }
        if (info.containsKey("CType")) {
            cType = info.get("CType");
        }
        List<ContactBook> contactBooks = contactService.listByUIdAndCNameOrCSexOrCType(uId, cName, cSex, cType);
        returnMsg.put("status", "200");
        returnMsg.put("message", contactBooks);
        return returnMsg.toJSONString();
    }

    @ApiOperation(value = "新增联系人接口",notes = "下面的参数显示有bug，没有显示说明，在此补充（具体的请看其他文档-实体类说明）：开头的字母c表示contact，u表示用户，如caddress实际为c_address或CAddress" )
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为新增的联系人的信息",dataTypeClass = com.moment.contact_book.entity.ContactBook.class)
    })
    @PostMapping("/insert")
    public String insertContact(@NotNull @Valid @RequestBody ContactBook contactBook) {
        JSONObject returnMsg = new JSONObject();
        ContactBook contact = contactService.insertContact(contactBook);
        if (contact == null) {
            throw new ControllerException("增加联系人错误：未知错误");
        }
        returnMsg.put("status", "200");
        returnMsg.put("message", contact);
        return returnMsg.toJSONString();
    }

    @ApiOperation(value = "新增联系人接口",notes = "下面的参数显示有bug，没有显示说明，在此补充（具体的请看其他文档-实体类说明）：开头的字母c表示contact，u表示用户，如caddress实际为c_address或CAddress")
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "成功时为更新后的联系人的信息",dataTypeClass = com.moment.contact_book.entity.ContactBook.class)
    })
    @PostMapping("/update")
    public String updateContact(@NotNull @Valid @RequestBody ContactBook contactBook) {
        JSONObject returnMsg = new JSONObject();
        ContactBook contact = contactService.updateInfoByUIdAndCId(contactBook);
        if (contact == null) {
            throw new ControllerException("修改联系人错误：未知错误");
        }
        returnMsg.put("status", "200");
        returnMsg.put("message", contact);
        return returnMsg.toJSONString();
    }

    @ApiOperation("删除指定联系人接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UId", value = "用户id"),
            @ApiImplicitParam(name = "CId", value = "联系人id")
    })
    @DynamicResponseParameters(properties={
            @DynamicParameter(name = "status",value = "状态码,成功是200,参数错误是400",example = "200"),
            @DynamicParameter(name = "message", value = "success")
    })
    @PostMapping("/delete")
    public String deleteContact(@NotNull(message = "用户id不得为空") String UId,
                                @NotNull(message = "联系人id不得为空") String CId) {
        JSONObject returnMsg = new JSONObject();
        int result = contactService.deleteContact(UId, CId);
        if (result != 1) {
            throw new ControllerException("修改联系人错误：未知错误");
        }
        returnMsg.put("status", "200");
        returnMsg.put("message", "success");
        return returnMsg.toJSONString();
    }
}
