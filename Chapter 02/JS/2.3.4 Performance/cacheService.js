const store = new Map();

const cache = {
    get: (key) => {
        console.log(`  [CACHE] Checking for key: ${key}`);
        if (store.has(key)) {
            console.log("  [CACHE] HIT! Returning data immediately. (takes 5ms)");
            return store.get(key);
        }
        console.log("  [CACHE] MISS!");
        return null;
    },
    set: (key, value, ttlSeconds) => {
        console.log(`  [CACHE] Saving data for key: ${key} (Expires in ${ttlSeconds}s)`);
        store.set(key, value);
    }
};

module.exports = cache;