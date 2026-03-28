package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability.after;

import java.util.List;

/**
 * Demonstrates a class that is easy to test by using Dependency Injection.
 */
public class ReportGenerator {
    // The class depends on an abstraction (an interface), not a concrete class.
    private final DatabaseConnection dbConnection;

    // IMPROVEMENT: Dependency is Injected (Loose Coupling)
    // Instead of creating its own dependency, the class receives it as a
    // constructor parameter. This is a common form of "Dependency Injection."
    //
    // WHY IS THIS GOOD FOR TESTABILITY?
    // 1. Loose Coupling: The `ReportGenerator` class is no longer tightly
    //    coupled to `RealDatabaseConnection`. It only knows about the `IDatabaseConnection`
    //    interface.
    // 2. Control Inversion: The control of which database connection to use has been
    //    "inverted." It's no longer the responsibility of this class; it's the
    //    responsibility of whoever *creates* this class.
    // 3. Mocking is Now Possible: In a test environment, we can create a "mock" or
    //    "fake" implementation of `IDatabaseConnection` and pass it to the constructor.
    //    This allows us to test the `ReportGenerator` in complete isolation,
    //    simulating different database scenarios (e.g., returning errors, empty data, etc.)
    //    without needing a real database.
    public ReportGenerator(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Generates a report using data from the injected database connection.
     * * @param reportName The name of the report to generate.
     * @return A string representing the generated report.
     */
    public String generate(String reportName) {
        List<String> data = this.dbConnection.getData(reportName);
        return String.format("Report '%s' generated with %d rows.", reportName, data.size());
    }
}
