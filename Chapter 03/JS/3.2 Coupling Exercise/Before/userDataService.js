/**
 * ANTI-PATTERN: The "Chatty" API and Tight Coupling.
 * * ARCHITECTURE PROBLEM: This service exposes highly granular methods. While this 
 * might seem like it promotes reuse, it forces the client to make multiple 
 * sequential calls to assemble a complete picture of a User.
 * * If this were a remote microservice or a database over a network, every single 
 * method call would incur latency. Furthermore, the client is forced to know 
 * too much about how to piece this data together.
 */
class UserDataService {
    
    /**
     * 🚨 ARCHITECTURE WARNING: Forces the client to make a separate call just for the name.
     */
    getUserName(userId) {
        console.log("    [Service] Fetching Name...");
        return "Jane Doe";
    }

    /**
     * 🚨 ARCHITECTURE WARNING: Forces the client to make a separate call just for the email.
     */
    getUserEmail(userId) {
        console.log("    [Service] Fetching Email...");
        return "jane.doe@example.com";
    }

    getUserOrderIds(userId) {
        console.log("    [Service] Fetching Order IDs...");
        return ["A123", "B456"];
    }

    /**
     * 🚨 ARCHITECTURE WARNING: High Coupling to Data Structure.
     * By forcing the client to fetch the total for each order individually, 
     * the service delegates its domain responsibility (calculating a user's total) 
     * to the client.
     */
    getOrderTotal(orderId) {
        console.log(`    [Service] Fetching Total for Order ${orderId}...`);
        return 99.95;
    }
}

module.exports = UserDataService;