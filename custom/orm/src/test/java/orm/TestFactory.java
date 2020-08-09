package orm;

import club.anlan.demo.custom.orm.bean.User;
import club.anlan.demo.custom.orm.factory.DBSessionFactory;
import club.anlan.demo.custom.orm.util.ORMAnnoHelper;
import club.anlan.demo.custom.orm.xmlparser.FactoryBuilder;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * test 类
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 0:51
 */
public class TestFactory {

    @Test
    public void test() {
        String xmlPath = TestFactory.class.getClassLoader().getResource("beans.xml").getFile();
        System.out.println(xmlPath);

        FactoryBuilder factoryBuilder = new FactoryBuilder(xmlPath);
        FactoryBuilder.Factory factory = factoryBuilder.newFactory();
        User user = (User) factory.getBean("anlanUser");
        System.out.println(user);

    }

    @Test
    public void testTable() {
        String tableName = ORMAnnoHelper.getTableName(User.class);
        System.out.println(tableName);
        for (Field field : User.class.getDeclaredFields()) {
            System.out.println(ORMAnnoHelper.getColumnName(field));
        }
    }

    @Test
    public void testQuery() {
        DBSessionFactory factory = new DBSessionFactory();

        try {
            List<User> users = factory.openSession().list(User.class);
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        DBSessionFactory factory = new DBSessionFactory();
        try {
            DBSessionFactory.DBSession session = factory.openSession();
            User user = new User();
            user.setId(Long.parseLong("123456789111"));
            user.setName("faker");
            user.setPassword("faker");
            user.setNickName("me");
            user.setPhone("12345678901");
            user.setEmail("123@qq.com");
            session.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() {
        DBSessionFactory factory = new DBSessionFactory();
        try {
            DBSessionFactory.DBSession session = factory.openSession();
            User user = new User();
            user.setId(Long.parseLong("123456789111"));
            user.setName("faker-update");
            user.setPassword("faker-update");
            user.setNickName("me");
            user.setPhone("12345678901");
            user.setEmail("123@qq.com");
            session.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
