package com.moment.contact_book.service.impl;

import com.moment.contact_book.entity.ContactType;
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
        return typeMapper.findByUId(userId);
    }

    @Override
    public ContactType selectType(String userId, String typeId) {
        log.info("select the type by UId:" + userId + "; TId:" + typeId);
        return typeMapper.findByUIdAndTypeId(userId, typeId);
    }

    @Override
    public ContactType changeType(ContactType contactType) {
        log.info("try to change type:" + contactType);
        if (typeMapper.findByUIdAndTypeId(contactType.getUId(), contactType.getTypeId()) != null) {
            if (typeMapper.updateInfoByUIdAndTypeId(contactType) == 1) {
                log.info("change success");
                return contactType;
            }
        }
        log.info("change failed");
        return null;
    }

    @Override
    public ContactType addType(String userId, String typeName) {
        log.info("try to add the type: " + typeName + "; by User: " + userId);
        if (userMapper.findById(userId) != null) {
            ContactType type = new ContactType();
            type.setUId(userId);
            type.setTypeId(ServiceUtils.generateShortUuid());
            type.setTypeName(typeName);
            typeMapper.insertType(type);
            log.info("add type success! the info is: " + type);
            return type;
        }
        log.info("add type fail");
        return null;
    }

    @Override
    public int deleteType(ContactType contactType) {
        log.info("try to delete the type: " + contactType);
        String uid = contactType.getUId();
        String tid = contactType.getTypeId();
        if (typeMapper.findByUIdAndTypeId(uid,tid) != null) {
            if (typeMapper.deleteByUidAndTypeId(uid,tid) == 1){
                return 1;
            }
        }
        return 0;
    }


}
