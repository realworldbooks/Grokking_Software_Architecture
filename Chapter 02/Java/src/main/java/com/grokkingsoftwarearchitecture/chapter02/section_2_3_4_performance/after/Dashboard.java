package com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.after;

import com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.DatabaseService;

/**
 * Represents a dashboard service that uses a cache to improve performance.
 * This class demonstrates the "Cache-Aside" pattern.
 */
public class Dashboard {
    // Using a constant for the cache's Time-To-Live (TTL) is a good practice.
    // It makes the code more readable and ensures the expiration policy is consistent.
    private static final int CACHE_TTL_SECONDS = 600; // 10 minutes
    
    private final DatabaseService databaseService = new DatabaseService();
    private final CacheService cache = new CacheService();

    /**
     * Gets a summary of dashboard data for a user, using a cache to optimize performance.
     * * @param userId The ID of the user.
     * @return An object containing the user's dashboard data.
     */
    public DashboardData getDashboardSummary(String userId) {
        String cacheKey = "dashboard:" + userId;

        // IMPROVEMENT: The "Cache-Aside" Pattern
        //
        // STEP 1: Check the cache first.
        // Before doing any expensive work, we check if the data we need is already
        // in our fast in-memory cache. A cache read is significantly faster (e.g., <5ms)
        // than a database query (e.g., 500ms).
        Object cachedDashboard = cache.get(cacheKey);
        
        // If `cachedDashboard` is not null, we have a "cache hit."
        // We can immediately return the cached data without touching the database.
        if (cachedDashboard != null) {
            return (DashboardData) cachedDashboard;
        }

        // STEP 2: Handle a "cache miss."
        // If the data is not in the cache, we proceed with the expensive operation:
        // fetching the data from the database.
        String profile = databaseService.getProfile(userId);
        String orders = databaseService.getOrders(userId);
        String activity = databaseService.getActivity(userId);

        DashboardData dashboardData = new DashboardData(profile, orders, activity);

        // STEP 3: Store the result in the cache.
        // Before returning the data, we save it to the cache. The next time this
        // method is called for the same user (within the TTL window), we'll get a
        // cache hit and avoid the database calls altogether.
        cache.set(cacheKey, dashboardData, CACHE_TTL_SECONDS);

        return dashboardData;
    }

    // A simple inner class to hold the returned data, taking the place of C#'s anonymous object
    public static class DashboardData {
        public final String profile;
        public final String orders;
        public final String activity;

        public DashboardData(String profile, String orders, String activity) {
            this.profile = profile;
            this.orders = orders;
            this.activity = activity;
        }
    }
}

