CREATE DATABASE `contact_book_system` CHARACTER SET 'utf8';

CREATE TABLE `contact_book_system`.`User` (
  `u_id` INT NOT NULL PRIMARY KEY COMMENT '用户id',
  `u_login_name` VARCHAR (10) NOT NULL UNIQUE COMMENT '用户名',
  `u_password` VARCHAR (16) NOT NULL COMMENT '用户密码',
  `u_name` VARCHAR(16) COMMENT '用户姓名',
  `u_phone` VARCHAR (20) UNIQUE COMMENT '用户电话',
  `u_sex` VARCHAR (5) COMMENT '用户性别' CHECK (
    `u_sex` = '男'
    OR `u_sex` = '女'
    OR `u_sex` = '其他'
  ),
  `u_email` VARCHAR (50) NOT NULL COMMENT '用户邮箱'
)ENGINE = InnoDB COMMENT '用户表';

CREATE TABLE `contact_book_system`.`Contact_Type` (
    `u_id` INT NOT NULL COMMENT '相关用户id',
    `type_id` INT AUTO_INCREMENT NOT NULL COMMENT '类型id',
    `type_name` VARCHAR(10) NOT NULL COMMENT '类型名称',
    `type_comment` VARCHAR(255) COMMENT '类型备注',
    PRIMARY KEY(`u_id`,`type_id`),
    CONSTRAINT `fk_type_u_id` FOREIGN KEY(`u_id`) REFERENCES `User`(`u_id`) ON DELETE CASCADE,
    INDEX `index_type_id`(`type_id`)
)ENGINE = InnoDB COMMENT '联系人类型表';

CREATE TABLE `contact_book_system`.`Contact_Book` (
  `c_id` INT NOT NULL COMMENT '联系人id',
  `c_name` VARCHAR(10) NOT NULL COMMENT '联系人名字',
  `c_phone` VARCHAR(20) NOT NULL COMMENT '联系人手机号码',
  `c_sex` VARCHAR (5) NOT NULL COMMENT '联系人性别' CHECK (
    c_sex = '男'
    OR c_sex = '女'
    OR c_sex = '其他'
  ),
  `c_type` INT COMMENT '联系人分类',
  `c_qq` VARCHAR(20) UNIQUE COMMENT '联系人QQ',
  `c_address` VARCHAR(20) COMMENT '联系人地址',
  `c_work` VARCHAR(20) COMMENT '联系人职务',
  `u_id` INT COMMENT '相关用户id',
  PRIMARY KEY(`u_id`,`c_id`),
  CONSTRAINT `fk_contact_u_id` FOREIGN KEY(`u_id`) REFERENCES `User`(`u_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_contact_c_type` FOREIGN KEY (`c_type`) REFERENCES `contact_type`(`type_id`) ON DELETE CASCADE,
  INDEX `index_c_id`(`c_id`),
  INDEX `index_u_id_c_id`(`u_id`,`c_id`)
)ENGINE = InnoDB COMMENT '通讯录表';

INSERT INTO `contact_book_system`.`user`(`u_id`, `u_login_name`, `u_password`, `u_name`, `u_phone`, `u_sex`, `u_email`)
    VALUES ('000001', 'momincong', 'momincong', '莫敏聪', '132456123', '男', 'momincong@foxmail.com');
INSERT INTO `contact_book_system`.`user`(`u_id`, `u_login_name`, `u_password`, `u_name`, `u_phone`, `u_sex`, `u_email`)
    VALUES ('000002', 'luyecheng', 'luyecheng', '卢烨成', '131332153', '男', 'luyecheng@qq.com');
INSERT INTO `contact_book_system`.`user`(`u_id`, `u_login_name`, `u_password`, `u_name`, `u_phone`, `u_sex`, `u_email`)
    VALUES ('000003', 'ningpeixi', 'ningpeixi', '宁培希', '198654646', '男', 'ningpeixi@qq.com');
INSERT INTO `contact_book_system`.`user`(`u_id`, `u_login_name`, `u_password`, `u_name`, `u_phone`, `u_sex`, `u_email`)
    VALUES ('000004', 'luzhengguo', 'luzhengguo', '陆政国', '321231355', '男', 'luzhengguo@qq.com');

INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (1, 1, '同学', '');
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (1, 2, '朋友', NULL);
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (2, 3, '同学', NULL);
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (2, 4, '朋友', NULL);
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (3, 5, '同学', NULL);
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (3, 6, '朋友', NULL);
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (4, 7, '同学', NULL);
INSERT INTO `contact_book_system`.`contact_type`(`u_id`, `type_id`, `type_name`, `type_comment`)
    VALUES (4, 8, '朋友', NULL);

INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (1, '陆政国', '31315451', '男', 1, '', '', '', 1);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (2, '宁培希', '32131565', '男', 1, NULL, NULL, NULL, 1);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (3, '卢烨成', '1651513', '男', 2, NULL, NULL, NULL, 1);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (1, '莫敏聪', '31511312', '男', 3, NULL, NULL, NULL, 2);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (2, '陆政国', '31315451', '男', 3, NULL, NULL, NULL, 2);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (3, '宁培希', '32131565', '男', 4, NULL, NULL, NULL, 2);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (1, '陆政国', '31315451', '男', 5, NULL, NULL, NULL, 3);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (2, '卢烨成', '1651513', '男', 5, NULL, NULL, NULL, 3);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (3, '莫敏聪', '31511312', '男', 6, NULL, NULL, NULL, 3);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (1, '宁培希', '32131565', '男', 7, NULL, NULL, NULL, 4);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (2, '卢烨成', '1651513', '男', 7, NULL, NULL, NULL, 4);
INSERT INTO `contact_book_system`.`contact_book`(`c_id`, `c_name`, `c_phone`, `c_sex`, `c_type`, `c_qq`, `c_address`, `c_work`, `u_id`)
    VALUES (3, '莫敏聪', '31511312', '男', 8, NULL, NULL, NULL, 4);

