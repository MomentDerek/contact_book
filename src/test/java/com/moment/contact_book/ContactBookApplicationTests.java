package com.moment.contact_book;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.mapper.UserMapper;
import com.moment.contact_book.service.impl.UserServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
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

    @Test
    public void UserSqlTest(){
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
        System.out.println(userService.login("moment","momincong").toString());
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


}
