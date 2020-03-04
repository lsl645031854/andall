package com.andall.sally.supply.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: lsl
 * @Description: 通用Redis帮助类
 * @Date: Created on 2:46 下午 2020/3/4
 */
@Component
public class CommonRedisHelper {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LOCK_PREFIX = "redis_lock";

    /**
     * 最终加强分布式锁:待修改
     * @param key key值
     * @return 是否获取到
     */
    public boolean lock(String key, long timeout) {
        String lock = LOCK_PREFIX + key;
        // 利用lambda表达式
        Boolean execute = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                long expireAt = System.currentTimeMillis() + timeout;
                Boolean acquire = redisConnection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
                if (acquire != null && acquire) {
                    return true;
                } else {
                    byte[] value = redisConnection.get(lock.getBytes());
                    if (Objects.nonNull(value) && value.length > 0) {
                        long expireTime = Long.parseLong(new String(value));
                        if (System.currentTimeMillis() - expireTime > timeout) {
                            // 如果锁已经过期,重新设置时间
                            byte[] oldValue = redisConnection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + timeout).getBytes());
                            // 防止死锁
                            if (oldValue == value) {
                                // 还没释放锁或key为空, 返回false
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        if (execute != null) {
            return execute;
        }

        return false;
    }
}
