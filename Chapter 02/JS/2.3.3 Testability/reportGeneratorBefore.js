const DatabaseConnection = require('./databaseConnection');

/**
 * @class ReportGeneratorBefore
 * @description Demonstrates a class that is difficult to test due to tight coupling.
 */
class ReportGeneratorBefore {
    constructor() {
        // PROBLEM: Hardcoded Dependency (Tight Coupling)
        // The constructor creates its own instance of `DatabaseConnection`.
        // This is called "tight coupling." The `ReportGeneratorBefore` class is
        // permanently and directly tied to the `DatabaseConnection` class.
        //
        // WHY IS THIS BAD FOR TESTABILITY?
        // 1. No Isolation: You cannot test `ReportGeneratorBefore` without also
        //    testing `DatabaseConnection`.
        // 2. Real External Services: Unit tests should be fast and self-contained.
        //    Because we are forced to use `DatabaseConnection`, our tests would
        //    need to connect to an actual database. This is slow and unreliable.
        // 3. No "Fakes" or "Mocks": We can't substitute a "fake" database
        //    connection for testing purposes, so we can't test how the
        //    generator behaves if the database returns an error or empty data.
        this.dbConnection = new DatabaseConnection("live_connection_string");
    }

    /**
     * Generates a report using data from the database.
     * @param {string} reportName - The name of the report to generate.
     * @returns {string} A string representing the generated report.
     */
    generate(reportName) {
        const data = this.dbConnection.getData(reportName);
        return `Report '${reportName}' generated with ${data.length} rows.`;
    }
}

module.exports = ReportGeneratorBefore;