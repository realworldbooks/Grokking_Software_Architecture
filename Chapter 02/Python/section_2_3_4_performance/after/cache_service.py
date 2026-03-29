class CacheService:
    """
    Simulates a simple in-memory cache service.
    
    ARCHITECTURAL NOTE: In a real-world, distributed application, you would 
    use a dedicated, centralized caching server like Redis or Memcached 
    to ensure all application instances share the same cache state.
    """

    def __init__(self):
        # A dictionary is the standard Python way to handle key-value pairs
        self._store = {}

    def get(self, key: str):
        """
        Attempts to retrieve an item from the cache.
        :return: The cached object if found; otherwise, None.
        """
        print(f"\n  [CACHE] Checking for key: '{key}'...")
        
        if key in self._store:
            print("  [CACHE] HIT! Returning data immediately. (Simulated time: <1ms)")
            return self._store[key]
        
        print("  [CACHE] MISS! Data not found.")
        return None

    def set(self, key: str, value: any, ttl_seconds: int):
        """
        Stores an item in the cache.
        """
        print(f"  [CACHE] Storing data for key: '{key}' (Expires in {ttl_seconds}s)")
        # Real-world caches like Redis handle TTL expiration automatically.
        self._store[key] = value