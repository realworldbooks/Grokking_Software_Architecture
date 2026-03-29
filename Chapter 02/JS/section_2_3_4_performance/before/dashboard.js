const DatabaseService = require('../databaseService'); // Reusing the same service

/**
 * Represents a dashboard service that fetches data directly from the database.
 * ARCHITECTURAL NOTE: This class demonstrates a performance-unaware implementation.
 * There is no memory layer protecting the database from repetitive queries.
 */
class Dashboard {
    #databaseService = new DatabaseService();

    /**
     * Gets a summary of dashboard data for a user.
     * @param {string} userId 
     * @returns {Promise<object>}
     */
    async getDashboardSummary(userId) {
        // PROBLEM: Poor Performance due to Expensive, Repetitive Calls
        // This method fetches all required data directly from the database
        // every single time it is called.
        //
        // WHY IS THIS BAD FOR PERFORMANCE?
        // 1. High Latency: Each call takes 500ms, totaling 1500ms per request.
        // 2. High Database Load: Repetitive strain on the database server.
        // 3. Not Scalable: The database becomes a bottleneck as users grow.
        const profile = await this.#databaseService.getProfile(userId);
        const orders = await this.#databaseService.getOrders(userId);
        const activity = await this.#databaseService.getActivity(userId);

        return { profile, orders, activity };
    }
}

module.exports = Dashboard;