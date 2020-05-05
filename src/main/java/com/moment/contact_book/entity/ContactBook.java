package com.moment.contact_book.entity;

import lombok.Data;

/**
 * @Description: 联系人实体类
 * @Author: Moment
 * @Date: 2020/5/3 9:05
 */

@Data
public class ContactBook {

    private String CId;

    private String CName;

    private String CPhone;

    private String CSex;

    private String CType;

    private String CQq;

    private String CAddress;

    private String CWork;

    private String UId;
}
