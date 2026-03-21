const databaseService = require('./databaseService');
const cache = require('./cacheService');

// Using a constant for the cache's Time-To-Live (TTL) is a good practice.
const CACHE_TTL_SECONDS = 600; // 10 minutes

/**
 * Gets a summary of dashboard data for a user, using a cache to optimize performance.
 * This function demonstrates the "Cache-Aside" pattern.
 * @param {string} userId - The ID of the user.
 * @returns {object} An object containing the user's dashboard data.
 */
function getDashboardSummaryAfter(userId) {
    const cacheKey = `dashboard:${userId}`;
    
    // IMPROVEMENT: The "Cache-Aside" Pattern
    //
    // STEP 1: Check the cache first.
    // Before doing expensive work, we check if the data is in our fast in-memory cache.
    const cachedDashboard = cache.get(cacheKey);
    
    // If we get a "cache hit," we can immediately return the cached data.
    if (cachedDashboard) {
        return cachedDashboard;
    }
    
    // STEP 2: Handle a "cache miss."
    // If the data is not in the cache, we proceed with the expensive operation.
    const profile = databaseService.getProfile(userId);
    const orders = databaseService.getOrders(userId);
    const activity = databaseService.getActivity(userId);
    
    const dashboardData = { profile, orders, activity };
    
    // STEP 3: Store the result in the cache.
    // Before returning, we save the data to the cache. The next request for this
    // user will get a cache hit.
    cache.set(cacheKey, dashboardData, CACHE_TTL_SECONDS);
    
    return dashboardData;
}

module.exports = getDashboardSummaryAfter;