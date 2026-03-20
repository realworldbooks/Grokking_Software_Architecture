using System;
using System.Collections.Generic;

namespace Chapter02.Performance;

public class CacheService
{
    private readonly Dictionary<string, object> _store = new();

    public object? Get(string key)
    {
        Console.WriteLine($"  [CACHE] Checking for key: {key}");
        if (_store.TryGetValue(key, out var value))
        {
            Console.WriteLine("  [CACHE] HIT! Returning data immediately. (takes 5ms)");
            return value;
        }
        Console.WriteLine("  [CACHE] MISS!");
        return null;
    }

    public void Set(string key, object value, int ttlSeconds)
    {
        Console.WriteLine($"  [CACHE] Saving data for key: {key} (Expires in {ttlSeconds}s)");
        _store[key] = value;
    }
}