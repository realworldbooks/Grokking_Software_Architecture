from database_service import DatabaseService
from cache_service import CacheService

# Using a constant for the cache's Time-To-Live (TTL) is a good practice.
CACHE_TTL_SECONDS = 600  # 10 minutes

class DashboardAfter:
    """
    Represents a dashboard service that uses a cache to improve performance.
    This class demonstrates the "Cache-Aside" pattern.
    """
    def __init__(self):
        self.database_service = DatabaseService()
        self.cache = CacheService()

    def get_dashboard_summary(self, user_id):
        """
        Gets a summary of dashboard data for a user, using a cache to optimize performance.
        
        Args:
            user_id (str): The ID of the user.
            
        Returns:
            dict: A dictionary containing the user's dashboard data.
        """
        cache_key = f"dashboard:{user_id}"
        
        # IMPROVEMENT: The "Cache-Aside" Pattern
        #
        # STEP 1: Check the cache first.
        cached_dashboard = self.cache.get(cache_key)
        
        # If we get a "cache hit," return the cached data immediately.
        if cached_dashboard:
            return cached_dashboard
            
        # STEP 2: Handle a "cache miss."
        # If the data is not in the cache, do the expensive work.
        profile = self.database_service.get_profile(user_id)
        orders = self.database_service.get_orders(user_id)
        activity = self.database_service.get_activity(user_id)
        
        dashboard_data = {
            "profile": profile,
            "orders": orders,
            "activity": activity
        }
        
        # STEP 3: Store the result in the cache for next time.
        self.cache.set(cache_key, dashboard_data, CACHE_TTL_SECONDS)
        
        return dashboard_data