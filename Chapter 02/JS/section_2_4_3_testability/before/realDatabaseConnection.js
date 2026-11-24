/**
 * This is the "real" or "production" implementation of the database connection.
 * It would contain the actual logic to connect to and query a live database.
 * In a real-world application, this class would use a library like Sequelize, 
 * Knex, or Mongoose to interact with a SQL or NoSQL database.
 * * ARCHITECTURAL NOTE: In this "Before" state, notice that this class does not 
 * implement any interface. It is a rigid, concrete implementation.
 */
class RealDatabaseConnection {
    #connectionString;

    /**
     * Initializes the real database connection.
     * @param {string} connectionString 
     */
    constructor(connectionString) {
        this.#connectionString = connectionString;
        // In a real application, this is where the connection would be established.
        console.log(`\n  [DB] Connecting to... ${this.#connectionString}`);
    }

    /**
     * Fetches data from the live database.
     * @param {string} query The query to execute.
     * @returns {string[]} A list of data rows from the real database.
     */
    getData(query) {
        // For demonstration purposes, we're just returning hardcoded data.
        // A real implementation would execute the query against the database
        // and return the results.
        console.log(`  [DB] Executing query: ${query}`);
        return ["real_data_row1", "real_data_row2"];
    }
}

module.exports = RealDatabaseConnection;