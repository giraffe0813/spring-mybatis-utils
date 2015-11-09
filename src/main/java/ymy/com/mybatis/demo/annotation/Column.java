package ymy.com.mybatis.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* 定义字段的注解*/
@Retention(RetentionPolicy.RUNTIME)
/*该注解只能用在成员变量上*/
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * 用来存放字段的名字 如果未指定列名，默认列名使用成员变量名
     *
     * @return
     */
    String name() default "";
}
