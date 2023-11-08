package com.andall.sally.supply.utils;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 发号器
 *
 * @author Neo
 * @since 2023/9/13 14:43
 */
@Service
public class SignalSenderService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${tiny.url.hash.id.salt:123456}")
    private String hashIdSalt = "123456";

    private final Hashids hashids = new Hashids(hashIdSalt, 6);

    private static final String MALL_ID_SENDER_INCR_KEY = "mall.signal.sender.incr.key";
    public static final int RANG_BATCH = 100000;

    private AtomicLong current;
    private Long end;

    /**
     * 初始化
     *
     * @author Neo
     * @since 2023/9/15 10:38
     */
    @PostConstruct
    public void init() {
        this.range();
    }


    /**
     * 批量获取固定长度的ID
     *
     * @author Neo
     * @since 2023/9/15 09:26
     */
    public List<String> getBase62Id(Integer num) {
        if (Objects.isNull(num) || num < 1) {
            return Collections.EMPTY_LIST;
        }
        List<String> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(this.getBase62Id());
        }
        return result;
    }

    /**
     * 获取固定长度的ID
     *
     * @author Neo
     * @since 2023/9/15 09:26
     */
    public String getBase62Id() {
        return this.encode(this.getId());
    }

    /**
     * 获取ID
     *
     * @author Neo
     * @since 2023/9/14 09:01
     */
    public List<Long> getId(Integer num) {
        if (Objects.isNull(num) || num < 1) {
            return Collections.EMPTY_LIST;
        }
        List<Long> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(this.getId());
        }
        return result;
    }

    /**
     * 获取ID
     *
     * @author Neo
     * @since 2023/9/14 09:01
     */
    public synchronized Long getId() {
        while (true) {
            long result = current.getAndIncrement();
            if (result > end) {
                this.range();
            } else {
                return result;
            }
        }
    }

    /**
     * 获取号段范围
     *
     * @author Neo
     * @since 2023/9/15 09:15
     */
    private synchronized void range() {
        if (Objects.nonNull(current) && current.get() <= end) {
            return;
        }
        this.end = redisTemplate.opsForValue().increment(MALL_ID_SENDER_INCR_KEY, RANG_BATCH);
        this.current = new AtomicLong(this.end - RANG_BATCH + 1);
    }


    /**
     * ID 编码
     *
     * @author Neo
     * @since 2023/9/15 09:43
     */
    public String encode(Long id) {
        return this.hashids.encode(this.getId());
    }


    /**
     * ID 解码
     *
     * @author Neo
     * @since 2023/9/15 09:44
     */
    public Long decode(String key) {
        return Long.valueOf(hashids.decodeHex(key));
    }


}
