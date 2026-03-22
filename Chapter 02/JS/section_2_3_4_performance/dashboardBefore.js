const databaseService = require('./databaseService');

/**
 * Gets a summary of dashboard data for a user by fetching fresh data every time.
 * This function demonstrates a performance-unaware implementation.
 * @param {string} userId - The ID of the user.
 * @returns {object} An object containing the user's dashboard data.
 */
function getDashboardSummaryBefore(userId) {
    // PROBLEM: Poor Performance due to Expensive, Repetitive Calls
    // This function fetches all required data directly from the database simulation
    // every single time it is called.
    //
    // WHY IS THIS BAD FOR PERFORMANCE?
    // 1. High Latency: In the real world, database queries are slow. If this
    //    endpoint is hit frequently, the user will experience significant delays.
    // 2. High Database Load: Calling the database for the same data repeatedly
    //    puts unnecessary strain on the database server.
    // 3. Not Scalable: As the number of users and requests grows, the database
    //    will quickly become a bottleneck.
    const profile = databaseService.getProfile(userId);
    const orders = databaseService.getOrders(userId);
    const activity = databaseService.getActivity(userId);
    
    return { profile, orders, activity };
}

module.exports = getDashboardSummaryBefore;