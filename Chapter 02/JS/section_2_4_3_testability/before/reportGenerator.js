const RealDatabaseConnection = require('./realDatabaseConnection');

/**
 * Demonstrates a class that is difficult to test due to tight coupling.
 */
class ReportGenerator {
    // This private field holds a direct reference to a concrete implementation.
    #dbConnection;

    constructor() {
        // PROBLEM: Hardcoded Dependency (Tight Coupling)
        // The constructor creates its own instance of `RealDatabaseConnection`.
        // This is called "tight coupling." The `ReportGenerator` class is
        // permanently and directly tied to the `RealDatabaseConnection` class.
        //
        // WHY IS THIS BAD FOR TESTABILITY?
        // 1. No Isolation: You cannot test `ReportGenerator` without also
        //    testing `RealDatabaseConnection`.
        // 2. Real External Services: Unit tests should be fast and self-contained.
        //    Because we are forced to use `RealDatabaseConnection`, our tests would
        //    need to connect to an actual database. This is slow, unreliable, and
        //    can have side effects.
        // 3. No "Fakes" or "Mocks": We can't substitute a "fake" or "mock" database
        //    connection for testing purposes. For example, we can't test how the
        //    generator behaves if the database returns an error or empty data.
        this.#dbConnection = new RealDatabaseConnection("live_connection_string");
    }

    /**
     * Generates a report using data from the database.
     * @param {string} reportName 
     * @returns {string}
     */
    generate(reportName) {
        // This method's logic is dependent on the concrete `RealDatabaseConnection`.
        const data = this.#dbConnection.getData(reportName);
        return `Report '${reportName}' generated with ${data.length} rows.`;
    }
}

module.exports = ReportGenerator;