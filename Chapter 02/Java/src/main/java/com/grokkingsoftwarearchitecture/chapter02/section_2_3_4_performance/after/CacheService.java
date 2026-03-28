package com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.after;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulates a simple in-memory cache service.
 * * In a real-world, distributed application, you would not use a simple Dictionary like this
 * because each instance of your application would have its own separate cache.
 * * Instead, you would use a dedicated, centralized caching server like Redis or Memcached.
 * A centralized cache is shared by all instances of your application, ensuring that
 * if one instance caches data, all other instances can benefit from it.
 */
public class CacheService {
    private final Map<String, Object> store = new HashMap<>();

    /**
     * Attempts to retrieve an item from the cache.
     * * @param key The key of the item to retrieve.
     * @return The cached object if found; otherwise, null.
     */
    public Object get(String key) {
        System.out.println("\n  [CACHE] Checking for key: '" + key + "'...");
        if (store.containsKey(key)) {
            System.out.println("  [CACHE] HIT! Returning data immediately. (Simulated time: 5ms)");
            return store.get(key);
        }
        System.out.println("  [CACHE] MISS! Data not found.");
        return null;
    }

    /**
     * Stores an item in the cache.
     * * @param key        The key to store the item under.
     * @param value      The object to store.
     * @param ttlSeconds The Time-To-Live (how long the item should stay in the cache).
     */
    public void set(String key, Object value, int ttlSeconds) {
        System.out.println("  [CACHE] Storing data for key: '" + key + "' (Expires in " + ttlSeconds + "s)");
        // This simple simulation doesn't actually implement TTL (expiration),
        // but in a real cache, the item would be automatically evicted after the TTL.
        store.put(key, value);
    }
}