const User = require('./user');

/**
 * Simulates a Data Access Layer (DAL) or "Service" class.
 * Its single responsibility is to handle all interactions with the database.
 */
class Database {
    /**
     * Fetches a user's data from the database.
     * @param {string} userId 
     * @returns {Promise<User|null>}
     * * ARCHITECTURAL NOTE: The Nullable Constraint
     * Returning null is an explicit design choice. It forces the calling 
     * code (the controller) to acknowledge and handle the possibility 
     * that the user may not exist.
     */
    async fetchUserDataAsync(userId) {
        // Simulating an asynchronous database call.
        if (userId === "User123") {
            return new User("User123", "Alice", "alice@example.com");
        }
        
        // If the user is not found, we return null to signal this to the caller.
        return null;
    }
}

module.exports = Database;