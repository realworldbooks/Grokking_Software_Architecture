package com.grokkingsoftwarearchitecture.chapter02.performance;
import java.util.HashMap;
import java.util.Map;

public class DashboardAfter {
    private static final int CACHE_TTL_SECONDS = 600; // 10 minutes
    
    private final DatabaseService databaseService = new DatabaseService();
    private final CacheService cache = new CacheService();

    public Map<String, String> getDashboardSummary(String userId) {
        String cacheKey = "dashboard:" + userId;

        // 1. Check the FAST cache first
        @SuppressWarnings("unchecked")
        Map<String, String> cachedDashboard = (Map<String, String>) cache.get(cacheKey);
        
        if (cachedDashboard != null) {
            return cachedDashboard;
        }

        // 2. Cache MISS. Do the slow work...
        String profile = databaseService.getProfile(userId);
        String orders = databaseService.getOrders(userId);
        String activity = databaseService.getActivity(userId);

        Map<String, String> dashboardData = new HashMap<>();
        dashboardData.put("profile", profile);
        dashboardData.put("orders", orders);
        dashboardData.put("activity", activity);

        // 3. Save the result using our constant
        cache.set(cacheKey, dashboardData, CACHE_TTL_SECONDS);

        return dashboardData;
    }
}