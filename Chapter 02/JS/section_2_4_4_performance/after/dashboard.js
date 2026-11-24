const DatabaseService = require('../databaseService'); // Reusing the same service
const CacheService = require('./cacheService');

/**
 * Represents a dashboard service that uses a cache to improve performance.
 * This class demonstrates the "Cache-Aside" pattern.
 */
class Dashboard {
    // Constant for the cache's Time-To-Live (TTL) - 10 minutes
    static #CACHE_TTL_SECONDS = 600; 
    
    #databaseService = new DatabaseService();
    #cache = new CacheService();

    /**
     * Gets a summary of dashboard data for a user, using a cache to optimize performance.
     * @param {string} userId 
     * @returns {Promise<object>}
     */
    async getDashboardSummary(userId) {
        const cacheKey = `dashboard:${userId}`;

        // IMPROVEMENT: The "Cache-Aside" Pattern
        
        // STEP 1: Check the cache first.
        const cachedDashboard = this.#cache.get(cacheKey);
        
        // If we have a "cache hit", return immediately and avoid async database calls.
        if (cachedDashboard !== null) {
            return cachedDashboard;
        }

        // STEP 2: Handle a "cache miss."
        // We only proceed with expensive await calls if the data is missing.
        const profile = await this.#databaseService.getProfile(userId);
        const orders = await this.#databaseService.getOrders(userId);
        const activity = await this.#databaseService.getActivity(userId);

        const dashboardData = { profile, orders, activity };

        // STEP 3: Store the result in the cache for future use.
        this.#cache.set(cacheKey, dashboardData, Dashboard.#CACHE_TTL_SECONDS);

        return dashboardData;
    }
}

module.exports = Dashboard;