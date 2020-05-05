package com.moment.contact_book.entity;

import lombok.Data;

/**
 * @Description: 联系人分类实体类
 * @Author: Moment
 * @Date: 2020/5/3 9:02
 */

@Data
public class ContactType {

    private String UId;

    private String TypeId;

    private String TypeName;

    private String TypeComment;
}
