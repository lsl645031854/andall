package com.andall.sally.supply.annotation;

import java.lang.annotation.*;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 21:08 2020/11/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TypeAnnotation {
    MsgTypeEnum value();
}
