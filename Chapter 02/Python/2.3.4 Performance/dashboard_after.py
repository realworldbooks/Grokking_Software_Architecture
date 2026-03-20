from database_service import DatabaseService
from cache_service import CacheService

CACHE_TTL_SECONDS = 600  # 10 minutes

class DashboardAfter:
    def __init__(self):
        self.database_service = DatabaseService()
        self.cache = CacheService()

    def get_dashboard_summary(self, user_id):
        cache_key = f"dashboard:{user_id}"
        
        # 1. Check the FAST cache first
        cached_dashboard = self.cache.get(cache_key)
        
        if cached_dashboard:
            return cached_dashboard
            
        # 2. Cache MISS. Do the slow work...
        profile = self.database_service.get_profile(user_id)
        orders = self.database_service.get_orders(user_id)
        activity = self.database_service.get_activity(user_id)
        
        dashboard_data = {
            "profile": profile,
            "orders": orders,
            "activity": activity
        }
        
        # 3. Save the result using our constant
        self.cache.set(cache_key, dashboard_data, CACHE_TTL_SECONDS)
        
        return dashboard_data