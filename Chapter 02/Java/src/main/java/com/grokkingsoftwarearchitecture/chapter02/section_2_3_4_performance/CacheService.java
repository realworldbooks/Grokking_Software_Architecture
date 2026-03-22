package com.grokkingsoftwarearchitecture.chapter02.performance;

import java.util.HashMap;
import java.util.Map;

public class CacheService {
    private final Map<String, Object> store = new HashMap<>();

    public Object get(String key) {
        System.out.println("  [CACHE] Checking for key: " + key);
        if (store.containsKey(key)) {
            System.out.println("  [CACHE] HIT! Returning data immediately. (takes 5ms)");
            return store.get(key);
        }
        System.out.println("  [CACHE] MISS!");
        return null;
    }

    public void set(String key, Object value, int ttlSeconds) {
        System.out.println("  [CACHE] Saving data for key: " + key + " (Expires in " + ttlSeconds + "s)");
        store.put(key, value);
    }
}