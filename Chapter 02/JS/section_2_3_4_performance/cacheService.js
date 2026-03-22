/**
 * @file Simulates a simple in-memory cache service.
 * 
 * In a real-world, distributed application, you would not use a simple Map like this
 * because each instance of your application would have its own separate cache.
 * 
 * Instead, you would use a dedicated, centralized caching server like **Redis** or **Memcached**.
 * A centralized cache is shared by all instances of your application.
 */

const store = new Map();

const cache = {
    /**
     * Attempts to retrieve an item from the cache.
     * @param {string} key - The key of the item to retrieve.
     * @returns {any | null} The cached object if found; otherwise, null.
     */
    get: (key) => {
        console.log(`\\n  [CACHE] Checking for key: '${key}'...`);
        if (store.has(key)) {
            console.log("  [CACHE] HIT! Returning data immediately. (Simulated time: 5ms)");
            return store.get(key);
        }
        console.log("  [CACHE] MISS! Data not found.");
        return null;
    },
    /**
     * Stores an item in the cache.
     * @param {string} key - The key to store the item under.
     * @param {any} value - The object to store.
     * @param {number} ttlSeconds - The Time-To-Live (how long the item should stay in the cache).
     */
    set: (key, value, ttlSeconds) => {
        console.log(`  [CACHE] Storing data for key: '${key}' (Expires in ${ttlSeconds}s)`);
        // This simple simulation doesn't actually implement TTL (expiration).
        store.set(key, value);
    }
};

module.exports = cache;