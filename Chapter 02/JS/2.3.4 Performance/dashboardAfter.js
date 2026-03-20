const databaseService = require('./databaseService');
const cache = require('./cacheService');

const CACHE_TTL_SECONDS = 600; // 10 minutes

function getDashboardSummaryAfter(userId) {
    const cacheKey = `dashboard:${userId}`;
    
    // 1. Check the FAST cache first
    const cachedDashboard = cache.get(cacheKey);
    
    if (cachedDashboard) {
        return cachedDashboard;
    }
    
    // 2. Cache MISS. Do the slow work...
    const profile = databaseService.getProfile(userId);
    const orders = databaseService.getOrders(userId);
    const activity = databaseService.getActivity(userId);
    
    const dashboardData = { profile, orders, activity };
    
    // 3. Save the result using our constant
    cache.set(cacheKey, dashboardData, CACHE_TTL_SECONDS);
    
    return dashboardData;
}

module.exports = getDashboardSummaryAfter;