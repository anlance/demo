package orm;

import club.anlan.demo.custom.orm.bean.User;
import club.anlan.demo.custom.orm.factory.DBSessionFactory;
import club.anlan.demo.custom.orm.util.ORMAnnoHelper;
import club.anlan.demo.custom.orm.xmlparser.FactoryBuilder;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

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
}
