package com.moment.contact_book.service;

import com.moment.contact_book.entity.ContactType;

import java.util.List;

/**
 * @Description: 联系人类型服务层
 * 实现：
 * 返回用户所设置的所有类型，
 * 返回某个类型，
 * 修改某个类型，
 * 增加某个类型，
 * 删除某个类型
 * @Author: Moment
 * @Date: 2020/5/5 16:06
 */
public interface TypeService {

    // 返回用户所设置的所有类型
    List<ContactType> allType(String  userId);

    // 返回某个类型
    ContactType selectType(String  userId, String  typeId);

    // 修改某个类型
    ContactType changeType(ContactType contactType);

    // 增加某个类型
    ContactType addType(String userId,String typeName);

    // 删除某个类型(返回1为成功，0为失败）
    int deleteType(ContactType contactType);

}
