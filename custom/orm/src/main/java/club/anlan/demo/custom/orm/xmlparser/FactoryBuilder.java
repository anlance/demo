package club.anlan.demo.custom.orm.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 设计工厂（容器类）构造器
 * <p>
 * 1. 解析指定的 xml 文件
 * 2. 创建获取 bean 类型的工厂类对象
 * 2.1 根据 id 获取 bean 的实例（反射）
 * 2.2 注入实体的属性值
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 0:11
 */
public class FactoryBuilder {

    private HashMap<String, BeanInfo> beanMap;

    public Factory newFactory() {
        return new Factory();
    }

    public FactoryBuilder(String xmlPath) {
        try {
            File file = new File(xmlPath);
            if(!file.exists()){
                throw new RuntimeException("文件不存在");
            }

            // 1. 创建 SAXParser 解析器工厂类对象
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // 2. 创建 SAXParser 解析器对象
            SAXParser parser = factory.newSAXParser();

            // 3. 开始解析 xml 文件
            parser.parse(new FileInputStream(xmlPath), new DefaultHandler() {
                private BeanInfo beanInfo;

                @Override
                public void startDocument() throws SAXException {
                    beanMap = new HashMap<String, BeanInfo>();
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if ("bean".equals(qName)) {
                        beanInfo = new BeanInfo();
                        beanInfo.setId(attributes.getValue("id"));
                        beanInfo.setClsName(attributes.getValue("class"));
                        beanInfo.setScope(attributes.getValue("scope"));
                        beanInfo.setProps(new ArrayList<PropsInfo>());
                    } else if ("property".equals(qName)) {
                        beanInfo.getProps().add(new PropsInfo(attributes.getValue("name"),
                                attributes.getValue("value"), attributes.getValue("type")));
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if ("bean".equals(qName)) {
                        beanMap.put(beanInfo.getId(), beanInfo);
                        beanInfo = null;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Factory {

        public Object getBean(String id) {
            BeanInfo beanInfo = beanMap.get(id);

            if (beanInfo == null){
                return null;
            }

            //通过反射实体化 bean 标签的 class 属性指定的类对象
            try {
                Class cls = Class.forName(beanInfo.getClsName());
                Object obj = cls.newInstance();

                for (PropsInfo pi : beanInfo.getProps()) {
                    Field field = cls.getDeclaredField(pi.getName());
                    field.setAccessible(true);
                    if ("long".equals(pi.getType())) {
                        field.set(obj, Long.parseLong(pi.getValue()));
                    } else if ("int".equals(pi.getType())) {
                        field.set(obj, Integer.parseInt(pi.getValue()));
                    } else if ("float".equals(pi.getType())) {
                        field.set(obj, Float.parseFloat(pi.getValue()));
                    } else {
                        field.set(obj, pi.getValue());
                    }
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
