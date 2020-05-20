package com.andall.sally.supply.current;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: lsl
 * @Description: 读写锁demo
 *      写-写 互斥
 *      读-写 互斥
 *      读-读 不互斥
 * @Date: Created on 14:48 2020/9/1
 */

class MyCache {
    private ReentrantReadWriteLock readAndWriteLock = new ReentrantReadWriteLock(true);
    // 读锁
    private ReentrantReadWriteLock.ReadLock readLock = readAndWriteLock.readLock();
    // 写锁
    private ReentrantReadWriteLock.WriteLock writeLock = readAndWriteLock.writeLock();

    public void get() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "\t 获取数据--开始");
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println(Thread.currentThread().getName() + "\t 获取数据--结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    public void put() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "\t 缓存数据  开始");
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println(Thread.currentThread().getName() + "\t 缓存数据  结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            new Thread(myCache::put, String.valueOf(i)).start();
        }

        for (int i = 6; i <= 10; i++) {
            new Thread(myCache::get, String.valueOf(i)).start();
        }

    }

}
