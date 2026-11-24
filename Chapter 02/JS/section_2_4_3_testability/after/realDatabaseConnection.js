/**
 * This is the "real" or "production" implementation of the database connection.
 * It would contain the actual logic to connect to and query a live database.
 * * ARCHITECTURAL NOTE: Even though JavaScript doesn't have formal interfaces like C#,
 * this class "implements" the implicit contract by providing a getData() method.
 */
class RealDatabaseConnection {
    #connectionString;

    /**
     * @param {string} connectionString 
     */
    constructor(connectionString) {
        this.#connectionString = connectionString;
        console.log(`\n  [DB] Connecting to... ${this.#connectionString}`);
    }

    /**
     * Fetches data from the live database.
     * @param {string} query 
     * @returns {string[]}
     */
    getData(query) {
        console.log(`  [DB] Executing query: ${query}`);
        return ["real_data_row1", "real_data_row2"];
    }
}

module.exports = RealDatabaseConnection;