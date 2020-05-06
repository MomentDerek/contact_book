package com.moment.contact_book.service.impl;

import com.moment.contact_book.entity.ContactBook;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.mapper.ContactBookMapper;
import com.moment.contact_book.mapper.ContactTypeMapper;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.ContactService;
import com.moment.contact_book.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

/**
 * @Description: 联系人service层的实现
 * @Author: Moment
 * @Date: 2020/5/6 9:04
 */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactBookMapper contactMapper;
    private final UserMapper userMapper;
    private final ContactTypeMapper typeMapper;

    @Autowired
    public ContactServiceImpl(ContactBookMapper contactMapper, UserMapper userMapper, ContactTypeMapper typeMapper) {
        this.contactMapper = contactMapper;
        this.userMapper = userMapper;
        this.typeMapper = typeMapper;
    }

    @Override
    public List<ContactBook> listByUId(String UId) {
        log.info("list contact by id");
        List<ContactBook> list = contactMapper.findByUIdAndCNameOrCSexOrCType(UId, null, null, null);
        if (list.isEmpty()) {
            log.info("the list is Empty!");
        } else {
            log.info("list success!");
        }
        return list;
    }

    @Override
    public List<ContactBook> listByULoginName(String ULoginName) {
        log.info("list contact by login name:"+ULoginName);
        User user = userMapper.findByLoginName(ULoginName);
        if (user != null) {
            log.info("list success!");
            return listByUId(user.getUId());
        }
        log.info("list fail");
        return null;
    }

    @Override
    public ContactBook listByUIdAndCId(String UId, String CId) {
        log.info("list contact by uid and cid: "+ UId+":"+CId);
        ContactBook result = contactMapper.findByUIdAndCId(UId, CId);
        if (result == null) {
            log.info("the result is null!");
        } else {
            log.info("list success: " + result.getCName());
        }
        return result;
    }

    @Override
    public List<ContactBook> listByUIdAndCNameOrCSexOrCType(String UId, String CName, String CSex, String CType) {
        log.info("list contact by id and other conditions");
        log.info("UId = " + UId);
        log.info("CName = " + CName);
        log.info("CSex = " + CSex);
        log.info("CType = " + CType);
        List<ContactBook> list = contactMapper.findByUIdAndCNameOrCSexOrCType(UId, CName, CSex, CType);
        if (list.isEmpty()) {
            log.info("the list is Empty!");
        } else {
            log.info("list success!");
        }
        return list;
    }

    @Override
    public ContactBook insertContact(String UId, String CId, String CName, String CPhone, String CSex) {
        log.info("insert the contact:");
        log.info("UId = " + UId);
        log.info("CId = " + CId);
        log.info("CName = " + CName);
        log.info("CSex = " + CSex);
        log.info("CType = " + CPhone);
        User user = userMapper.findById(UId);
        if (userMapper.findById(UId) != null & contactMapper.findByUIdAndCId(UId, CId) == null) {
            if (contactMapper.insertContact(UId, CId, CName, CPhone, CSex) == 1) {
                return contactMapper.findByUIdAndCId(UId, CId);
            }
            log.info("unknown insert error");
        }
        log.info("some wrong with the uid and cid");
        return null;
    }

    @Override
    public ContactBook updateInfoByUIdAndCId(ContactBook contact) {
        log.info("update the contact:");
        log.info(contact.toString());
        if (typeMapper.findByUIdAndTypeId(contact.getUId(), contact.getCType()) == null) {
            log.info("update failed: the type is wrong");
            return null;
        }
        if (contactMapper.updateInfoByCIdAndUId(contact) == 1) {
            log.info("update success");
            return contact;
        }
        log.info("update failed");
        return null;
    }

    @Override
    public int deleteContact(String UId, String CId) {
        log.info("delete the contact: " + UId + ": " + CId);
        int result = contactMapper.deleteContactByCIdAndUId(UId, CId);
        log.info("delete result: " + result);
        return result;
    }
}
