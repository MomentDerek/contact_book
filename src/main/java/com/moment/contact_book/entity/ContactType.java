package com.moment.contact_book.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @Description: 联系人分类实体类
 * @Author: Moment
 * @Date: 2020/5/3 9:02
 */

@ApiModel("联系人类别类")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ContactType {

    @NonNull
    private String UId;

    @NonNull
    private String TypeId;

    @NonNull
    private String TypeName;

    private String TypeComment;
}
