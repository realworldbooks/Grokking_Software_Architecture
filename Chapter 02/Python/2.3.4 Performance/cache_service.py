class CacheService:
    """
    Simulates a simple in-memory cache service.
    
    In a real-world, distributed application, you would use a dedicated, 
    centralized caching server like **Redis** or **Memcached**.
    """
    def __init__(self):
        self._store = {}

    def get(self, key):
        """
        Attempts to retrieve an item from the cache.
        
        Args:
            key (str): The key of the item to retrieve.
            
        Returns:
            The cached object if found; otherwise, None.
        """
        print(f"\\n  [CACHE] Checking for key: '{key}'...")
        if key in self._store:
            print("  [CACHE] HIT! Returning data immediately. (Simulated time: 5ms)")
            return self._store[key]
        print("  [CACHE] MISS! Data not found.")
        return None

    def set(self, key, value, ttl_seconds):
        """
        Stores an item in the cache.
        
        Args:
            key (str): The key to store the item under.
            value: The object to store.
            ttl_seconds (int): The Time-To-Live (how long the item should stay in the cache).
        """
        print(f"  [CACHE] Storing data for key: '{key}' (Expires in {ttl_seconds}s)")
        # This simple simulation doesn't actually implement TTL (expiration).
        self._store[key] = value