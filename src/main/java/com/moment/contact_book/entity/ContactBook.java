package com.moment.contact_book.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 联系人实体类
 * @Author: Moment
 * @Date: 2020/5/3 9:05
 */

@ApiModel("联系人类")
@Data
public class ContactBook {

    @NotNull
    private String CId;

    @NotNull
    private String CName;

    @NotNull
    private String CPhone;

    @NotNull
    private String CSex;

    private String CType;

    private String CQq;

    private String CAddress;

    private String CWork;

    @NotNull
    private String UId;
}
