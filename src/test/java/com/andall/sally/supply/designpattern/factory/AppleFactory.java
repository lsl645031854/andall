package com.andall.sally.supply.designpattern.factory;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:42 2020/3/23
 */
public class AppleFactory implements FruitFactory<Apple> {
    @Override
    public Apple create(Apple apple) {
        return new Apple();
    }

    @Override
    public String desc() {
        return "i am apple ~~~";
    }

}