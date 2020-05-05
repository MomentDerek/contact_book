package com.moment.contact_book.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.moment.contact_book.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: 查询，修改，删除用户信息
 * @Author: Moment
 * @Date: 2020/5/3 20:25
 */
@Mapper
@Repository
public interface UserMapper {

    //根据登陆名查找用户
    User findByLoginName(@Param("u_login_name") String loginName);

    //根据id查找用户
    User findById(@Param("u_id") String id);

    User findByEmail(@Param("u_email") String email);

    //根据u_id, u_login_name, u_password, u_email创建用户
    int insertUser(@Param("User") User user);

    //根据登陆名修改密码
    int updatePasswordByLoginName(@Param("u_login_name") String loginName, @Param("u_password") String newPassword);

    //根据id修改用户信息
    int updateInfoById(@Param("User") User user);

    //注销用户
    int deleteUserById(@Param("u_id") String id);

}
