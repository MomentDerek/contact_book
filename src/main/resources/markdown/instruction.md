# 实体类说明

> 程序中用的是驼峰，数据库中用的是下划线，请自行转换

## 用户类

**以下必须非空**

- 用户id：UId;

- 用户登录名（用户名）ULoginName;

- 用户密码：UPassword;

- 用户姓名：UName;

- 用户邮箱：UEmail;

**以下可以留空**

- 用户手机号：UPhone;

- 用户性别：USex;

## 联系人类别类

**以下必须非空**

- 用户id：UId;

- 联系人类别id：TypeId;

- 联系人类别名称：TypeName;

**以下可以留空**

- 联系人类别说明TypeComment;

## 联系人类

**以下必须非空**

- 用户id：UId;

- 联系人id：CId;

- 联系人名称：CName;

- 联系人手机：CPhone;

- 联系人性别：CSex;

**以下可以留空**

- 联系人类别id：CType;

- 联系人qq：CQq;

- 联系人地址：CAddress;

- 联系人职位：CWork;

