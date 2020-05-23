package com.moment.contact_book;

import com.moment.contact_book.entity.ContactBook;
import com.moment.contact_book.entity.ContactType;
import com.moment.contact_book.mapper.ContactBookMapper;
import com.moment.contact_book.service.ContactService;
import com.moment.contact_book.service.TypeService;
import com.moment.contact_book.service.UserService;
import com.moment.contact_book.util.ServiceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.moment.contact_book.entity.User;
import com.moment.contact_book.mapper.UserMapper;
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
    private ContactBookMapper contactMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private ContactService contactService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContactBookApplicationTests.class);

    @Test
    public void UserSqlTest() {
        User user = userMapper.findByLoginName("momincong");
        assertThat(user).isNotNull();
        assertThat(user.getULoginName()).isEqualTo("momincong");
    }

    @Test
    public void ContactSqlTest() {
        List<ContactBook> contacts = contactMapper.findByUIdAndCNameOrCSexOrCType("000001",null,null,null);
        log.info(contacts.toString());
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
        log.info(userService.login("moment", "momincong").toString());
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
        log.info(allTypes.toString());
        assertThat(allTypes.contains(new ContactType("000001", "000002", "朋友"))).isTrue();
        // 返回某个类型
        ContactType type = typeService.selectType("000001", "000000");
        assertThat(type).isNull();
        type = typeService.selectType("000001", "000002");
        log.info(type.toString());
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

    @Test
    public void ContactServiceTest() {
        // 根据u_id列出联系人
        List<ContactBook> list1 = contactService.listByUId("000001");
        assertThat(list1.isEmpty()).isFalse();
        log.info(list1.toString());
        // 根据用户名列出联系人
        list1 = contactService.listByULoginName("luzhengguo");
        assertThat(list1.isEmpty()).isFalse();
        log.info(list1.toString());
        // 根据u_id和c_id查询指定联系人
        ContactBook contact = contactService.listByUIdAndCId("000001", "000001");
        assertThat(contact).isNotNull();
        log.info(contact.toString());
        // 根据u_id和name,sex,type三项中的若干项查询联系人
        list1 = contactService.listByUIdAndCNameOrCSexOrCType("000001", "陆政国", null, null);
        assertThat(list1.isEmpty()).isFalse();
        log.info(list1.toString());
        list1 = contactService.listByUIdAndCNameOrCSexOrCType("000001", "陆政国", "女", null);
        assertThat(list1.isEmpty()).isTrue();
        // 插入联系人(中途改了接口,这个作废)
        // contact = contactService.insertContact("000001", ServiceUtils.generateShortUuid(), "霍洁瑜", "32151323", "女");
        // assertThat(contact).isNotNull();
        // log.info(contact.toString());
        // 修改联系人信息
        contact.setCType("000003");
        assertThat(contactService.updateInfoByUIdAndCId(contact)).isNull();
        contact.setCType("000002");
        assertThat(contactService.updateInfoByUIdAndCId(contact)).isNotNull();
        log.info(contact.toString());
        // 删除联系人
        contactService.deleteContact(contact.getUId(), contact.getCId());
        assertThat(contactService.listByUId("000001").contains(contact)).isFalse();
    }

}
