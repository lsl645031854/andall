package com.andall.sally.supply.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 9:30 上午 2021/3/10
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncAnnotation {
    // 序号
    int sort();

    Class<?> type();

    // 是否赋值
    boolean valid() default true;

}
