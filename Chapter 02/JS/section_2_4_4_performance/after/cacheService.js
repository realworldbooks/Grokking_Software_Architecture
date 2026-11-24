/**
 * Simulates a simple in-memory cache service.
 * * ARCHITECTURAL NOTE: In a real-world, distributed application, you would 
 * use a dedicated, centralized caching server like Redis or Memcached 
 * rather than a local Map. This ensures that if one instance of your 
 * app caches data, all other instances can benefit from it.
 */
class CacheService {
    // A Map is the standard JavaScript way to handle key-value pairs
    #store = new Map();

    /**
     * Attempts to retrieve an item from the cache.
     * @param {string} key 
     * @returns {any|null} The cached object if found; otherwise, null.
     */
    get(key) {
        console.log(`\n  [CACHE] Checking for key: '${key}'...`);
        
        if (this.#store.has(key)) {
            console.log("  [CACHE] HIT! Returning data immediately. (Simulated time: <1ms)");
            return this.#store.get(key);
        }
        
        console.log("  [CACHE] MISS! Data not found.");
        return null;
    }

    /**
     * Stores an item in the cache.
     * @param {string} key 
     * @param {any} value 
     * @param {number} ttlSeconds 
     */
    set(key, value, ttlSeconds) {
        console.log(`  [CACHE] Storing data for key: '${key}' (Expires in ${ttlSeconds}s)`);
        // In a real cache like Redis, the TTL would be handled automatically.
        this.#store.set(key, value);
    }
}

module.exports = CacheService;