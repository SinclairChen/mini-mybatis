package com.sinclair.mybaits.minimybatis.annotation;

import java.lang.annotation.*;

/**
 * @Description 注解方法，配置sql语句
 * @Date 2021/2/16 16:01
 * @Author by Saiyong.Chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
