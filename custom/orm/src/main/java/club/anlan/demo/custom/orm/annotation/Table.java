package club.anlan.demo.custom.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * table 注解
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 22:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    // 声明注解属性

    String value() default "";
}
