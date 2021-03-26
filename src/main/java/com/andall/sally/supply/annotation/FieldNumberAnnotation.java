package com.andall.sally.supply.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 9:50 上午 2021/3/10
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldNumberAnnotation {
    int fieldNum();
}
