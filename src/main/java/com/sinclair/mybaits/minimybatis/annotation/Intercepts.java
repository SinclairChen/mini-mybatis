package com.sinclair.mybaits.minimybatis.annotation;

import java.lang.annotation.*;

/**
 * @Description
 * @Date 2021/2/16 16:00
 * @Author by Saiyong.Chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Intercepts {

    String value();
}
