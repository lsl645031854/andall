package com.andall.sally.supply.Interview.lru;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:50 2020/8/7
 */
public interface LRUCache<K, V> {
    void put(K key, V v);

    V get(K key);

    void remove(K key);

    int size();

    void clear();

    int limit();
}
