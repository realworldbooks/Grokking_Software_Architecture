/**
 * @class DatabaseConnection
 * @description This is the "real" or "production" implementation of a database connection.
 * In a real-world application, this class would use a library like `node-postgres`,
 * `mysql2`, or an ORM like Sequelize to interact with a live database.
 */
class DatabaseConnection {
    /**
     * @param {string} connectionString - The database connection string.
     */
    constructor(connectionString) {
        // In a real application, this is where the connection would be established.
        console.log(`\n  [DB] Connecting to... ${connectionString}`);
    }

    /**
     * Fetches data from the live database.
     * @param {string} query - The query to execute.
     * @returns {Array<string>} A list of data rows from the real database.
     */
    getData(query) {
        // For demonstration purposes, we're just returning hardcoded data.
        console.log(`  [DB] Executing query: ${query}`);
        return ["real_data_row1", "real_data_row2"];
    }
}

module.exports = DatabaseConnection;