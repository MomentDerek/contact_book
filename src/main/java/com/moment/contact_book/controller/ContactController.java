package com.moment.contact_book.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONObject;
import com.moment.contact_book.entity.ContactBook;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.service.ContactService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Description: 联系人的Controller层
 * @Author: Moment
 * @Date: 2020/5/16 13:15
 */
@RestController
@Validated
@RequestMapping("/user/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/all")
    public String listAll(@NotNull(message = "查询信息不得为空") Map<String, String> info) {
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

    @PostMapping("/search")
    public String searchContact(@NotNull(message = "查询信息不得为空") Map<String, String> info) {
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
