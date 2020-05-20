package com.andall.sally.supply.guava;

import com.andall.sally.supply.entity.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:53 2020/6/29
 */
public class GuavaTest {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(200), new ThreadPoolExecutor.CallerRunsPolicy());

    private static ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(threadPoolExecutor);

    private static Map<String, User> userMap = new HashMap<>();

    static {
        userMap.put("aaa", new User("tom", "123", 18));
        userMap.put("bbb", new User("mary", "456", 21));
        userMap.put("ccc", new User("alex", "789", 20));
    }

    @Test
    public void test() throws Exception {
        Map<String, Object> map1 = basicDataCache.getIfPresent("aaa");
        System.out.println(map1);
        Map<String, Object> map2 = basicDataCache.get("bbb");
        System.out.println(map2);

        basicDataCache.invalidate("bbb"); // 失效

        ConcurrentMap<String, Map<String, Object>> stringMapConcurrentMap = basicDataCache.asMap();
        System.out.println(stringMapConcurrentMap);

        Map<String, Object> map = basicDataCache.get("ccc");

        Map<String, Object> ccc = basicDataCache.getIfPresent("ccc");
        System.out.println(map);
        System.out.println(ccc);

    }

    private final LoadingCache<String, Map<String, Object>> basicDataCache = CacheBuilder.newBuilder()
            // 设置并发级别为cpu核心数
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            // 设置初始容量为100
            .initialCapacity(100)
            // 设置缓存在写入一定时间后，通过CacheLoader的load方法进行刷新
            .refreshAfterWrite(5, TimeUnit.SECONDS).build(new CacheLoader<String, Map<String, Object>>() {
        @Override
        @ParametersAreNonnullByDefault
        public Map<String, Object> load(String key) {
            System.out.println("load cache");
            return initBasicData(key);
        }

        @Override
        public ListenableFuture<Map<String, Object>> reload(final String key, Map<String, Object> oldValue) {
            System.out.println("reload cache");
            return listeningExecutorService.submit(() -> load(key));
        }
    });

    private Map<String, Object> initBasicData(String key) {
        User user = userMap.get(key);
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUserName());
        map.put("age", user.getAge());
        return map;
    }
}
