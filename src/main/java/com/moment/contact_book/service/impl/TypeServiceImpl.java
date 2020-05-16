package com.moment.contact_book.service.impl;

import com.moment.contact_book.entity.ContactType;
import com.moment.contact_book.exception.ServiceException;
import com.moment.contact_book.mapper.ContactTypeMapper;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.TypeService;
import com.moment.contact_book.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TypeService的实现
 * @Author: Moment
 * @Date: 2020/5/5 16:40
 */

@Slf4j
@Service
public class TypeServiceImpl implements TypeService {

    private final ContactTypeMapper typeMapper;
    private final UserMapper userMapper;

    @Autowired
    public TypeServiceImpl(ContactTypeMapper typeMapper, UserMapper userMapper) {
        this.typeMapper = typeMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<ContactType> allType(String userId) {
        log.info("select the types by UId:" + userId);
        List<ContactType> list = typeMapper.findByUId(userId);
        if (null == list) {
            log.error("find all type fail");
            throw new ServiceException("联系人类型查询失败");
        }
        else if (list.isEmpty()) {
            log.info("no type");
            throw new ServiceException("没有设置联系人类型");
        }
        return list;
    }

    @Override
    public ContactType selectType(String userId, String typeId) {
        log.info("select the type by UId:" + userId + "; TId:" + typeId);
        ContactType type = typeMapper.findByUIdAndTypeId(userId, typeId);
        if (null == type) {
            log.error("select type fail");
            throw new ServiceException("该联系人类型查询失败");
        }
        return type;
    }

    @Override
    public ContactType changeType(ContactType contactType) {
        log.info("change type:" + contactType);
        if (typeMapper.findByUIdAndTypeId(contactType.getUId(), contactType.getTypeId()) != null) {
            if (typeMapper.updateInfoByUIdAndTypeId(contactType) == 1) {
                log.info("change success");
                return contactType;
            }
        }
        log.info("change failed");
        throw new ServiceException("联系人类型更新失败");
    }

    @Override
    public ContactType addType(String userId, String typeName) {
        log.info("add the type: " + typeName + "; by User: " + userId);
        if (userMapper.findById(userId) != null) {
            ContactType type = new ContactType();
            type.setUId(userId);
            type.setTypeId(ServiceUtils.generateShortUuid());
            type.setTypeName(typeName);
            if( typeMapper.insertType(type) != 1){
                log.error("add type fail, the info is wrong");
                throw new ServiceException("联系人类型添加失败, 请检查类型信息");
            }
            log.info("add type success! the info is: " + type);
            return type;
        }
        log.info("add type fail, wrong user");
        throw new ServiceException("用户信息错误,未找到该用户");
    }

    @Override
    public int deleteType(ContactType contactType) {
        log.info("delete the type: " + contactType);
        String uid = contactType.getUId();
        String tid = contactType.getTypeId();
        if (typeMapper.findByUIdAndTypeId(uid,tid) != null) {
            if (typeMapper.deleteByUidAndTypeId(uid, tid) == 1) {
                return 1;
            } else {
                log.info("delete type fail, the info is wrong");
                throw new ServiceException("类型信息错误,请检查类型信息");
            }
        }
        log.info("delete type fail, wrong user");
        throw new ServiceException("用户信息错误,未找到该用户");
    }


}
