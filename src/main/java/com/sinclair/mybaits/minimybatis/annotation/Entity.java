package com.sinclair.mybaits.minimybatis.annotation;

import java.lang.annotation.*;

/**
 * @Description 用于注解接口
 * @Date 2021/2/16 15:59
 * @Author by Saiyong.Chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

    Class<?> value();
}
