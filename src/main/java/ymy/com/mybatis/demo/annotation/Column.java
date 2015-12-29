package ymy.com.mybatis.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
/*can only be used on attribute*/
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * represent the database's filed, if empty use attribute's name as filed
     *
     * @return
     */
    String name() default "";
}
