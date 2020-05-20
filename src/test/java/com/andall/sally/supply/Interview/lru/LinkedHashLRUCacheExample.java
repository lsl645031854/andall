package com.andall.sally.supply.Interview.lru;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:51 2020/8/7
 */
public class LinkedHashLRUCacheExample {
    public static void main(String[] args) {
        cache();
    }

    private static void cache() {
        LRUCache<String, String> cache = new LinkedHashLRUCache<>(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");

        cache.get("3");
        System.out.println(cache.toString());
    }
}
