package com.moment.contact_book.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @Description: 用户实体类
 * @Author: Moment
 * @Date: 2020/5/3 8:56
 */

@ApiModel("用户类")
@Data
public class User {

    @NotNull
    private String UId;

    @NotNull
    private String ULoginName;

    @NotNull
    private String UPassword;

    @NotNull
    private String UName;

    private String UPhone;

    private String USex;

    @NotNull
    @Email
    private String UEmail;
}
