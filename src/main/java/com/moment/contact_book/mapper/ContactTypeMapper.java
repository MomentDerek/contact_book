package com.moment.contact_book.mapper;

import com.moment.contact_book.entity.ContactType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 用户类别表mapper
 * @Author: Moment
 * @Date: 2020/5/4 13:14
 */
@Mapper
public interface ContactTypeMapper {

    //根据u_id,type_id查找类别
    ContactType findByUIdAndTypeId(@Param("u_id") int UId, @Param("type_id") int TypeId);

    //根据u_id查找类别
    List<ContactType> findById(@Param("u_id") long id);

    //根据u_id, type_id, type_name, type_comment创建类别
    int insertType(@Param("Type") ContactType type);

    //根据u_id和type_id修改类别信息
    int updateInfoByUIdAndTypeId(@Param("Type") ContactType type);

    //根据u_id和type_id删除类别
    int deleteByUidAndTypeId(@Param("u_id") int UId, @Param("type_id") int TypeId);

}
