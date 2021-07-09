package com.andall.sally.supply.Interview;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: lsl
 * @Description: 手写自旋锁
 * @Date: Created on 09:26 2020/8/7
 */
public class SpinLockDemo {

    //原子引用线程
    private final AtomicReference<Thread> atomicReference = new AtomicReference<>();

    private void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + " come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
        System.out.println(Thread.currentThread().getName() + " get lock");
    }

    private void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + " invoke myUnLock");
    }

    public static void main(String[] args) {

        //线程操作资源类
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "AAA").start();

        //保证A先启动、然后获得锁
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "BBB").start();

    }
}
