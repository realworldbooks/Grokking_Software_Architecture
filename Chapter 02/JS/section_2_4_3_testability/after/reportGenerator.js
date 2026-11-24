/**
 * Demonstrates a class that is easy to test by using Dependency Injection.
 */
class ReportGenerator {
    #dbConnection;

    /**
     * IMPROVEMENT: Dependency is Injected (Loose Coupling)
     * Instead of creating its own dependency, the class receives it as a 
     * constructor parameter. 
     * * WHY IS THIS GOOD FOR TESTABILITY?
     * 1. Loose Coupling: This class is no longer tied to RealDatabaseConnection.
     * 2. Control Inversion: The responsibility of choosing the database 
     * is moved to the caller.
     * 3. Mocking: We can now pass a "fake" implementation to test the 
     * generator logic without needing a real network or database.
     */
    constructor(dbConnection) {
        this.#dbConnection = dbConnection;
    }

    /**
     * Generates a report using data from the injected database connection.
     * @param {string} reportName 
     * @returns {string}
     */
    generate(reportName) {
        const data = this.#dbConnection.getData(reportName);
        return `Report '${reportName}' generated with ${data.length} rows.`;
    }
}

module.exports = ReportGenerator;