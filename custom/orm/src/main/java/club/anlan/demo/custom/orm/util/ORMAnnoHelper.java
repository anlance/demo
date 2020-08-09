package club.anlan.demo.custom.orm.util;

import club.anlan.demo.custom.orm.annotation.Column;
import club.anlan.demo.custom.orm.annotation.Table;

import java.lang.reflect.Field;

/**
 * 注解解析
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 23:02
 */
public class ORMAnnoHelper {

    /**
     * @param beanCls
     * @return 指定类上注入的表名
     */
    public static String getTableName(Class<?> beanCls) {
        Table tableAnno = beanCls.getAnnotation(Table.class);
        if (tableAnno == null) {
            return beanCls.getSimpleName().toLowerCase();
        }
        return tableAnno.value();
    }

    /**
     * @param field
     * @return 指定字段
     */
    public static String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return field.getName().toLowerCase();
        }
        return column.value();
    }

    public static boolean isId(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            return column.isId();
        }
        return false;
    }
}
