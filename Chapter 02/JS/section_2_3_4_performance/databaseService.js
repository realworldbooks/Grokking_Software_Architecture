/**
 * @file Simulates a slow, expensive database service.
 * 
 * NOTE: In a real-world Node.js application, all I/O operations (like database calls)
 * MUST be asynchronous to avoid blocking the event loop. These functions would
 * return Promises, and you would use `async/await` to handle them.
 * 
 * For simplicity in this demonstration, we are using synchronous functions.
 */
const databaseService = {
    /**
     * Simulates fetching a user profile from the database.
     * @param {string} id The user's ID.
     */
    getProfile: (id) => { 
        console.log(`    [DB] Fetching Profile for ${id}... (Simulating 500ms latency)`);
        // In a real async function, you would see `await sleep(500);`
        return "User_Profile_Data";
    },
    /**
     * Simulates fetching a user's orders from the database.
     * @param {string} id The user's ID.
     */
    getOrders: (id) => { 
        console.log(`    [DB] Fetching Orders for ${id}... (Simulating 500ms latency)`);
        return "User_Orders_Data";
    },
    /**
     * Simulates fetching a user's activity from the database.
     * @param {string} id The user's ID.
     */
    getActivity: (id) => { 
        console.log(`    [DB] Fetching Activity for ${id}... (Simulating 500ms latency)`);
        return "User_Activity_Data";
    }
};

module.exports = databaseService;