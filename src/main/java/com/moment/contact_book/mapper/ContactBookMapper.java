package com.moment.contact_book.mapper;

import com.moment.contact_book.entity.ContactBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 联系人表mapper
 * @Author: Moment
 * @Date: 2020/5/4 13:39
 */
@Mapper
public interface ContactBookMapper {

    //根据u_id和c_id查找联系人
    ContactBook findByUIdAndCId(@Param("u_id") int UId, @Param("c_id") int CId);

    //根据u_id和特定条件查找联系人(此时要注意起码要有一个条件存在)
    ContactBook findByUIdAndCNameOrCSexOrCType(@Param("u_id") int UId,
                                               @Param("c_name") String CName,
                                               @Param("c_sex") String CSex,
                                               @Param("c_type") String CType);

    //根据c_id, c_name, c_phone, c_sex, u_id插入联系人
    int insertContact(@Param("u_id") int UId,
                      @Param("c_id") int CId,
                      @Param("c_name") String CName,
                      @Param("c_phone") String CPhone,
                      @Param("c_sex") String CSex);

    //根据c_id, u_id修改联系人信息
    int updateInfoByCIdAndUId(@Param("ContactBook") ContactBook contactBook);

    //删除联系人
    int deleteContactByCIdAndUId(@Param("u_id") int UId, @Param("c_id") int CId);

}
