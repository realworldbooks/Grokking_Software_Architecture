const databaseService = require('./databaseService');

function getDashboardSummaryBefore(userId) {
    // Brute Force. Do the slow work every single time...
    const profile = databaseService.getProfile(userId);
    const orders = databaseService.getOrders(userId);
    const activity = databaseService.getActivity(userId);
    
    return { profile, orders, activity };
}

module.exports = getDashboardSummaryBefore;