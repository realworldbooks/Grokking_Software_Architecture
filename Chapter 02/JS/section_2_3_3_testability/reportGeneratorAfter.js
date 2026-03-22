/**
 * @class ReportGeneratorAfter
 * @description Demonstrates a class that is easy to test by using Dependency Injection.
 */
class ReportGeneratorAfter {
    /**
     * @param {object} dbConnection - An object that has a `getData` method.
     */
    constructor(dbConnection) {
        // IMPROVEMENT: Dependency is Injected (Loose Coupling)
        // Instead of creating its own dependency, the class receives it as a
        // constructor parameter. This is a common form of "Dependency Injection."
        //
        // WHY IS THIS GOOD FOR TESTABILITY?
        // 1. Loose Coupling: The `ReportGeneratorAfter` is no longer tightly
        //    coupled to a specific `DatabaseConnection` implementation. It just needs
        //    *any* object that has a `getData` method (this is called "Duck Typing").
        // 2. Control Inversion: The control of which database connection to use has been
        //    "inverted." It's no longer the responsibility of this class.
        // 3. Mocking is Now Possible: In a test environment, we can create a simple "fake"
        //    object or class and pass it to the constructor. This allows us to test
        //    `ReportGeneratorAfter` in complete isolation.
        this.dbConnection = dbConnection;
    }

    /**
     * Generates a report using data from the injected database connection.
     * @param {string} reportName - The name of the report to generate.
     * @returns {string} A string representing the generated report.
     */
    generate(reportName) {
        const data = this.dbConnection.getData(reportName);
        return `Report '${reportName}' generated with ${data.length} rows.`;
    }
}

module.exports = ReportGeneratorAfter;