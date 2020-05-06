package com.moment.contact_book;

import com.moment.contact_book.entity.ContactType;
import com.moment.contact_book.service.TypeService;
import com.moment.contact_book.util.ServiceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.impl.UserServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactBookApplication.class)
@AutoConfigureTestDatabase(replace = NONE)
class ContactBookApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TypeService typeService;

    @Test
    public void UserSqlTest() {
        User user = userMapper.findByLoginName("momincong");
        assertThat(user).isNotNull();
        assertThat(user.getULoginName()).isEqualTo("momincong");
    }

    @Test
    public void UserServiceTest() {
        //测试登录
        User login = userService.login("momincong", "momincong");
        assertThat(login.getULoginName()).isEqualTo("momincong");
        login = userService.login("momincongmoment", "momincong");
        assertThat(login).isNull();
        //测试注册
        int result = userService.register("moment", "momincong", "1026821350@qq.com");
        assertThat(result).isEqualTo(1);
        //测试获取用户信息
        System.out.println(userService.login("moment", "momincong").toString());
        //测试修改用户密码
        assertThat(userService.changePassword("moment", "momincong", "moment")).isEqualTo(1);
        login = userService.login("moment", "moment");
        assertThat(login).isNotNull();
        //测试修改用户数据
        login.setUName("momincong");
        assertThat(userService.changeInfo(login)).isEqualTo(login);
        //测试删除用户数据
        assertThat(userService.deleteUser(login.getULoginName(), login.getUPassword())).isEqualTo(1);
    }

    @Test
    public void TypeServiceTest() {
        // 测试返回用户的所有类型
        List<ContactType> allTypes = typeService.allType("000001");
        System.out.println(allTypes);
        assertThat(allTypes.contains(new ContactType("000001", "000002", "朋友"))).isTrue();
        // 返回某个类型
        ContactType type = typeService.selectType("000001", "000000");
        assertThat(type).isNull();
        type = typeService.selectType("000001", "000002");
        System.out.println(type);
        assertThat(type).isEqualTo(new ContactType("000001", "000002", "朋友"));
        // 修改某个类型
        ContactType changeType = new ContactType("000001", "000001", "同学");
        changeType.setTypeComment(ServiceUtils.generateShortUuid());
        assertThat(typeService.changeType(changeType)).isEqualTo(changeType);
        // 增加某个类型
        assertThat(typeService.addType("000009", "儿子")).isNull();
        ContactType newType = typeService.addType("000001", "儿子");
        assertThat(newType).isNotNull();
        // 删除某个类型(返回1为成功，0为失败）
        assertThat(typeService.deleteType(newType)).isEqualTo(1);
    }


}
