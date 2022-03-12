package com.andall.sally.supply.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel注解映射文档行标题
 * @author xubangbang
 * @date 2021/7/20 13:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelFields {

    String value();
}
