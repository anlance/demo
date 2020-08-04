package orm;

import club.anlan.demo.custom.orm.bean.User;
import club.anlan.demo.custom.orm.xmlparser.FactoryBuilder;
import org.junit.Test;

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
}
