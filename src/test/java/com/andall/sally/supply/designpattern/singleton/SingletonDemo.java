package com.andall.sally.supply.designpattern.singleton;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:03 2020/9/19
 */
public class SingletonDemo {

    private static volatile SingletonDemo instance = null;

    private SingletonDemo () {
        System.out.println("实例化....");
    }

    // DCL 双端检锁机制 double check lock
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

}
