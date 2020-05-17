package com.moment.contact_book.service.impl;

import com.moment.contact_book.entity.ContactBook;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.exception.ServiceException;
import com.moment.contact_book.mapper.ContactBookMapper;
import com.moment.contact_book.mapper.ContactTypeMapper;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //校验UID和CID
    private void CheckCIdUId(String CId, String UId) {
        if (UId == null || UId.equals("")) {
            log.info("listByUId fail: the uid is emtpy");
            throw new ServiceException("用户id不能为空");
        }
        if (CId == null || CId.equals("")) {
            log.info("listByUId fail: the cid is emtpy");
            throw new ServiceException("联系人id不能为空");
        }
        if (contactMapper.findByUIdAndCId(UId, CId) == null) {
            log.info("update failed: the info is wrong");
            throw new ServiceException("联系人信息错误，请检查");
        }
    }

    @Override
    public List<ContactBook> listByUId(String UId) {
        if (UId == null || UId.equals("")) {
            log.info("listByUId fail: the uid is emtpy");
            throw new ServiceException("用户id不能为空");
        }
        log.info("list contact by id:" + UId);
        User user = userMapper.findById(UId);
        if (user == null) {
            log.info("list contact fail, cannot find the user");
            throw new ServiceException("用户信息查询失败");
        }
        List<ContactBook> list = contactMapper.findByUIdAndCNameOrCSexOrCType(UId, null, null, null);
        //idea提示封装了方法，具体请看方法内部
        return returnUserList(list);
    }

    @Override
    public List<ContactBook> listByULoginName(String ULoginName) {
        if (ULoginName == null || ULoginName.equals("")) {
            log.info("listByULoginName fail: the ULoginName is emtpy");
            throw new ServiceException("用户名不能为空");
        }
        log.info("list contact by login name:" + ULoginName);
        User user = userMapper.findByLoginName(ULoginName);
        if (user == null) {
            log.info("list fail, cannot find the user");
            throw new ServiceException("用户信息查询失败");
        }
        List<ContactBook> list = listByUId(user.getUId());
        //idea提示封装了方法，具体请看方法内部
        return returnUserList(list);
    }

    private List<ContactBook> returnUserList(List<ContactBook> list) {
        if (list == null) {
            log.error("list contact fail: database error");
            throw new ServiceException("数据库查询失败，请联系管理员");
        }
        if (list.isEmpty()) {
            log.info("the list is Empty!");
            throw new ServiceException("该用户无联系人");
        } else {
            log.info("list success!");
        }
        return list;
    }

    @Override
    public ContactBook listByUIdAndCId(String UId, String CId) {
        CheckCIdUId(CId, UId);
        log.info("list contact by uid and cid: " + UId + ":" + CId);
        ContactBook result = contactMapper.findByUIdAndCId(UId, CId);
        if (result == null) {
            log.info("the result is null!");
            throw new ServiceException("查询失败，请确认查询信息");
        } else {
            log.info("list success: " + result.getCName());
        }
        return result;
    }

    @Override
    public List<ContactBook> listByUIdAndCNameOrCSexOrCType(String UId, String CName, String CSex, String CType) {
        if (UId == null || UId.equals("")) {
            log.info("listByUId fail: the uid is emtpy");
            throw new ServiceException("用户id不能为空");
        }
        log.info("list contact by id and other conditions");
        log.info("UId = " + UId);
        log.info("CName = " + CName);
        log.info("CSex = " + CSex);
        log.info("CType = " + CType);
        List<ContactBook> list = contactMapper.findByUIdAndCNameOrCSexOrCType(UId, CName, CSex, CType);
        if (list.isEmpty()) {
            log.info("the list is Empty!");
            throw new ServiceException("查询无结果");
        } else {
            log.info("list success!");
        }
        return list;
    }

    @Override
    public ContactBook insertContact(ContactBook contact) {
        CheckCIdUId(contact.getCId(), contact.getUId());
        log.info("insert the contact:");
        log.info("UId = " + contact.getUId());
        log.info("CId = " + contact.getCId());
        log.info("CName = " + contact.getCName());
        log.info("CSex = " + contact.getCSex());
        if (1 == contactMapper.insertContact(
                contact.getUId(),
                contact.getCId(),
                contact.getCName(),
                contact.getCPhone(),
                contact.getCSex())) {
            return contactMapper.findByUIdAndCId(contact.getUId(), contact.getCId());
        }
        log.info("unknown insert error");
        throw new ServiceException("新增联系人失败：未知原因");
    }

    @Override
    public ContactBook updateInfoByUIdAndCId(ContactBook contact) {
        String CId = contact.getCId();
        String UId = contact.getUId();
        String CType = contact.getCType();
        CheckCIdUId(CId, UId);
        log.info("update the contact:");
        log.info(contact.toString());
        if (CType != null && !CType.equals("")) {
            if (typeMapper.findByUIdAndTypeId(UId, CType) == null) {
                log.info("update failed: the type is wrong");
                throw new ServiceException("找不到联系人类型信息，请检查");
            }
        }
        if (contactMapper.updateInfoByCIdAndUId(contact) == 1) {
            log.info("update success");
            return contact;
        }
        throw new ServiceException("更新联系人信息失败：未知原因");
    }

    @Override
    public int deleteContact(String UId, String CId) {
        CheckCIdUId(CId,UId);
        log.info("delete the contact: " + UId + ": " + CId);
        int result = contactMapper.deleteContactByCIdAndUId(UId, CId);
        log.info("delete result: " + result);
        return result;
    }
}
