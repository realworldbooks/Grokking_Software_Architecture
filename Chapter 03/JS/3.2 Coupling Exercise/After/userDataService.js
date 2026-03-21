/**
 * THE SOLUTION: The "Chunky" API and High Cohesion.
 * * ARCHITECTURE NOTE: The service has taken back its rightful responsibilities!
 * Instead of forcing the client to orchestrate the data gathering, the service 
 * now does the heavy lifting internally. 
 * * By exposing a single, coarse-grained ("chunky") method that returns a plain 
 * JavaScript object (acting as our Data Transfer Object), we eliminate the 
 * Chatty API problem. This minimizes potential network round-trips and completely 
 * encapsulates the internal complexity of how an order total is calculated.
 */
class UserDataService {
    
    /**
     * Assembles the complete user report internally and returns a single payload.
     * @param {number} userId - The ID of the user.
     * @returns {Object} The chunky report data payload.
     */
    getUserReport(userId) {
        console.log("    [Service] Building chunky report payload internally...");
        
        // The service now handles all the internal database fetching and 
        // math, keeping the client completely ignorant of the details!
        return {
            name: "Jane Doe",
            email: "jane.doe@example.com",
            totalSpent: 199.90
        };
    }
}

module.exports = UserDataService;