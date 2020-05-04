package com.moment.contact_book.entity;

import lombok.Data;

/**
 * @Description: 用户实体类
 * @Author: Moment
 * @Date: 2020/5/3 8:56
 */

@Data
public class User {

    private Integer UId;

    private String ULoginName;

    private String UPassword;

    private String UName;

    private String UPhone;

    private String USex;

    private String UEmail;
}
