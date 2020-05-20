package com.andall.sally.supply.current;

import org.junit.Test;


class ModifyNum {
    private volatile int num = 30;

    void modify() {
        System.out.println(Thread.currentThread().getName() + " :" + num);
        num = num + 10;
        System.out.println(Thread.currentThread().getName() + " :" + num);

    }
}

/**
 * @Author: lsl
 * @Description: volatile 可见性 不保证原子性  禁止指令重排
 * @Date: Created on 09:35 2020/8/10
 */
public class VolatileTest {

    @Test
    public void test() {

        ModifyNum modifyNum = new ModifyNum();

        // 测试可见性
        new Thread(modifyNum::modify, "AAA").start();

        new Thread(modifyNum::modify, "BBB").start();

    }

}
