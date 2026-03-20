class CacheService:
    def __init__(self):
        self._store = {}

    def get(self, key):
        print(f"  [CACHE] Checking for key: {key}")
        if key in self._store:
            print("  [CACHE] HIT! Returning data immediately. (takes 5ms)")
            return self._store[key]
        print("  [CACHE] MISS!")
        return None

    def set(self, key, value, ttl_seconds):
        print(f"  [CACHE] Saving data for key: {key} (Expires in {ttl_seconds}s)")
        self._store[key] = value