/**
 * Simulates a slow, expensive database service.
 * In a real application, these methods would contain logic to query a database.
 * * ARCHITECTURAL NOTE: In this "Before" state, every single request to the 
 * dashboard will be forced to wait for these slow network calls to finish.
 */
class DatabaseService {
    /**
     * Simulates the real-world latency of network I/O and query execution.
     * @returns {Promise<void>}
     */
    #simulateNetworkLatency() {
        return new Promise(resolve => setTimeout(resolve, 500));
    }

    /**
     * Simulates fetching a user profile from the database.
     * @param {string} id 
     */
    async getProfile(id) {
        console.log(`    [DB] Fetching Profile for ${id}...`);
        await this.#simulateNetworkLatency();
        console.log("    [DB] >> Profile data received.");
        return "User_Profile_Data";
    }

    /**
     * Simulates fetching a user's orders from the database.
     * @param {string} id 
     */
    async getOrders(id) {
        console.log(`    [DB] Fetching Orders for ${id}...`);
        await this.#simulateNetworkLatency();
        console.log("    [DB] >> Order data received.");
        return "User_Orders_Data";
    }

    /**
     * Simulates fetching a user's activity from the database.
     * @param {string} id 
     */
    async getActivity(id) {
        console.log(`    [DB] Fetching Activity for ${id}...`);
        await this.#simulateNetworkLatency();
        console.log("    [DB] >> Activity data received.");
        return "User_Activity_Data";
    }
}

module.exports = DatabaseService;