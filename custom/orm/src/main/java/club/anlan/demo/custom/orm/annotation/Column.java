package club.anlan.demo.custom.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * column
 *
 * @author lan
 * @version 1.0
 * @date 2020/8/5 22:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    String value() default "";

    boolean isNull() default true;

    boolean isId() default false;
}
