/**
 * @file Simulates a Data Access Layer (DAL) or "Service" module.
 * 
 * Its single responsibility is to handle all interactions with the database.
 * This separation of concerns means that if we were to change our database technology
 * (e.g., from a file system to a real MongoDB or PostgreSQL database), this is 
 * the only module we would need to modify. The controller would remain unchanged.
 */
const database = {
    /**
     * Fetches a user's data from the database.
     * @param {string} userId - The ID of the user to fetch.
     * @returns {Promise<object|null>} A Promise that resolves to a user object if found, otherwise null.
     * Returning null is an explicit design choice to signal that the user was not found,
     * allowing the controller to handle this specific business case.
     */
    fetchUserData: async (userId) => {
        // In a real application, this would be an asynchronous call to a database.
        // e.g., `await UserModel.findById(userId);`
        if (userId === "User123") {
            return {
                id: "User123",
                name: "Alice",
                email: "alice@example.com"
            };
        }
        // If the user is not found, we resolve the promise with null.
        return null;
    }
};

module.exports = database;