package com.andall.sally.supply.Interview.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:49 2020/8/7
 */
public class LinkedHashLRUCache<K, V> implements LRUCache<K, V> {
    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {
        private static final long serialVersionUID = -3902069512296355750L;
        final private int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > limit;
        }
    }

    private final int limit;
    private final InternalLRUCache<K, V> internalLRUCache;

    public LinkedHashLRUCache(int limit) {
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V v) {
        this.internalLRUCache.put(key, v);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return this.internalLRUCache.toString();
    }
}
