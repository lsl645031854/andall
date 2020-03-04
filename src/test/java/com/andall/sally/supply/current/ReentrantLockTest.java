package com.andall.sally.supply.current;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:52 下午 2020/3/4
 */
public class ReentrantLockTest {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        lock.lock();

        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName());
        };
        new Thread(r).start();

    }

}
