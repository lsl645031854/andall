package com.andall.sally.supply.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


public class RedissonUtils {

    private  static RedissonClient redissonClient;

    private static Integer defaultExpireTime = 10;

    public static void setRedissonClient(RedissonClient redissonClient) {
        RedissonUtils.redissonClient = redissonClient;
    }

    public static boolean isLock(String key ){
        RLock lock = redissonClient.getLock(key);
        return lock.isLocked();
    }

    public static void lock(String key){
        lock(key,defaultExpireTime);
    }

    public static void lock(String key ,Integer secondes){
        RLock lock = redissonClient.getLock(key);
        lock.lock(secondes, TimeUnit.SECONDS);
    }

    public static void unlock(String key){
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    public static <T> T lockAndUnlock(String key,Supplier<T> supplier){
        try {
            RedissonUtils.lock(key);
            return supplier.get();
        }finally {
            RedissonUtils.unlock(key);
        }
    }

    public static <T> T lockAndUnlock(String key,Integer expire,Supplier<T> supplier){
        try {
            RedissonUtils.lock(key,expire);
            return supplier.get();
        }finally {
            RedissonUtils.unlock(key);
        }
    }

}
