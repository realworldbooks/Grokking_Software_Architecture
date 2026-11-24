from ..database_service import DatabaseService
from .cache_service import CacheService

class Dashboard:
    """
    Represents a dashboard service that uses a cache to improve performance.
    This class demonstrates the 'Cache-Aside' pattern.
    """
    
    # 10 minutes TTL
    CACHE_TTL_SECONDS = 600

    def __init__(self):
        self._database_service = DatabaseService()
        self._cache = CacheService()

    def get_dashboard_summary(self, user_id: str) -> dict:
        """
        Gets a summary of dashboard data for a user using the Cache-Aside pattern.
        """
        cache_key = f"dashboard:{user_id}"

        # STEP 1: Check the cache first.
        cached_dashboard = self._cache.get(cache_key)
        
        # If we have a "cache hit", return immediately.
        if cached_dashboard is not None:
            return cached_dashboard

        # STEP 2: Handle a "cache miss."
        # We only hit the slow database if the data is missing from the cache.
        profile = self._database_service.get_profile(user_id)
        orders = self._database_service.get_orders(user_id)
        activity = self._database_service.get_activity(user_id)

        dashboard_data = {
            "profile": profile, 
            "orders": orders, 
            "activity": activity
        }

        # STEP 3: Store the result in the cache for future use.
        self._cache.set(cache_key, dashboard_data, self.CACHE_TTL_SECONDS)

        return dashboard_data