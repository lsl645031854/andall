package com.andall.sally.supply.current;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lsl
 * @Description: 比较并交换
 * @Date: Created on 11:12 2020/9/19
 */
public class CasDemo {

    /**
     * CAS底层原理 自旋锁 UnSafe 类 是一条cpu并发原语，
     *
     * 1.Unsafe 是 Cas的核心类，由于Java无法直接访问底层系统    需要通过本地的native方法来访问
     * Unsafe相当于一个后门，可以直接操作特定内存的数据，ubsafe位于 sun.misc包中
     * Unsafe所有的方法都是native修饰的，说明可以直接调用操作系统底层资源执行相应任务
     * 2. 变量valueOffSet,表示内存的偏移地址
     * 3. value 需要用volatile修饰
     */
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        // compareAndSet(expect， update)
        atomicInteger.compareAndSet(0, 2019);

        System.out.println(atomicInteger.get());

    }
}
