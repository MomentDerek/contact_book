package com.moment.contact_book.service;

import com.moment.contact_book.entity.ContactBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 联系人服务层
 * 实现：
 * 根据u_id列出联系人
 * 根据用户名列出联系人
 * 根据u_id和c_id查询指定联系人
 * 根据u_id和name,sex,type三项中的若干项查询联系人
 * 插入联系人
 * 修改联系人信息
 * 删除联系人
 * @Author: Moment
 * @Date: 2020/5/6 8:37
 */
public interface ContactService {


    // 根据u_id列出联系人
    List<ContactBook> listByUId(String UId);

    // 根据用户名列出联系人
    List<ContactBook> listByULoginName(String ULoginName);

    // 根据u_id和c_id查询指定联系人
    ContactBook listByUIdAndCId(String UId, String CId);

    // 根据u_id和name,sex,type三项中的若干项查询联系人
    List<ContactBook> listByUIdAndCNameOrCSexOrCType(String UId,
                                                     String CName,
                                                     String CSex,
                                                     String CType);

    // 插入联系人
    ContactBook insertContact(ContactBook contact);

    // 修改联系人信息
    ContactBook updateInfoByUIdAndCId(ContactBook contact);

    // 删除联系人
    int deleteContact(String UId, String CId);

}
