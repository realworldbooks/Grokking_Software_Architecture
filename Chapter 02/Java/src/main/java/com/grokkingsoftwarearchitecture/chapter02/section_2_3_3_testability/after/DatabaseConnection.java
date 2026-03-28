package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability.after;

import java.util.List;

/**
 * Defines the "contract" for a database connection.
 * An interface is an abstract type that only defines the public members (methods, properties)
 * that a class MUST implement if it implements the interface.
 * * By creating this contract, other classes (like `ReportGenerator`) can depend on
 * the `IDatabaseConnection` abstraction rather than a specific, concrete database class.
 * This is the key to achieving loose coupling and enabling dependency injection.
 */
public interface DatabaseConnection {
    /**
     * Fetches data from the database based on a query.
     * * @param query The query to execute.
     * @return A list of strings representing the data rows.
     */
    List<String> getData(String query);
}