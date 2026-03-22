using System;
using System.Collections.Generic;

namespace Chapter02.Performance;

/// <summary>
/// Simulates a simple in-memory cache service.
/// 
/// In a real-world, distributed application, you would not use a simple Dictionary like this
/// because each instance of your application would have its own separate cache.
/// 
/// Instead, you would use a dedicated, centralized caching server like **Redis** or **Memcached**.
/// A centralized cache is shared by all instances of your application, ensuring that
/// if one instance caches data, all other instances can benefit from it.
/// </summary>
public class CacheService
{
    private readonly Dictionary<string, object> _store = new();

    /// <summary>
    /// Attempts to retrieve an item from the cache.
    /// </summary>
    /// <param name="key">The key of the item to retrieve.</param>
    /// <returns>The cached object if found; otherwise, null.</returns>
    public object? Get(string key)
    {
        Console.WriteLine($"\\n  [CACHE] Checking for key: '{key}'...");
        if (_store.TryGetValue(key, out var value))
        {
            Console.WriteLine("  [CACHE] HIT! Returning data immediately. (Simulated time: 5ms)");
            return value;
        }
        Console.WriteLine("  [CACHE] MISS! Data not found.");
        return null;
    }

    /// <summary>
    /// Stores an item in the cache.
    /// </summary>
    /// <param name="key">The key to store the item under.</param>
    /// <param name="value">The object to store.</param>
    /// <param name="ttlSeconds">The Time-To-Live (how long the item should stay in the cache).</param>
    public void Set(string key, object value, int ttlSeconds)
    {
        Console.WriteLine($"  [CACHE] Storing data for key: '{key}' (Expires in {ttlSeconds}s)");
        // This simple simulation doesn't actually implement TTL (expiration),
        // but in a real cache, the item would be automatically evicted after the TTL.
        _store[key] = value;
    }
}