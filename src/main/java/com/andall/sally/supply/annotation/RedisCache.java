package com.andall.sally.supply.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lsl
 * @Description: 自定义注解缓存.
 * @Date: Created on 12:02 2020/6/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    // 缓存的key
    String key();

    // 缓存时间 0表示无缓存时间
    long expire() default 0L;

    // 缓存的类型 ModelType
    Class type();

    // 是否走缓存
    boolean cache() default false;
}
