package com.andall.sally.supply.Interview;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: lsl
 * @Description: 写一个模拟的分布式缓存（Redis），写的时候要求⼀个线程进去，读的时候允许多个进去。 写缓存的3大基本方法：写、读、清空
 * @Date: Created on 09:45 2020/8/7
 */
public class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入：" + key);

            //暂停一会模拟写入的而过程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }

            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写入完成：");
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public void get(String key) {
        rwlock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取：");

            //暂停一会模拟读出的而过程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Object res = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完成：" + res);
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public void clear() {
        map.clear();
    }
}
