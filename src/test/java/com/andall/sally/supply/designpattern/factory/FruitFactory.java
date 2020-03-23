package com.andall.sally.supply.designpattern.factory;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:40 2020/3/23
 */
public interface FruitFactory<T> {
    T create(T t);

    String desc();
}
