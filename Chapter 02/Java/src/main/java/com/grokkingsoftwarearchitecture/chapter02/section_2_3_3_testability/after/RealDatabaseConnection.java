package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability.after;

import java.util.Arrays;
import java.util.List;

/**
 * This is the "real" or "production" implementation of the `IDatabaseConnection` interface.
 * It would contain the actual logic to connect to and query a live database.
 * In a real-world application, this class would use a library like Hibernate or JDBC
 * to interact with a SQL Server, PostgreSQL, or other database.
 */
public class RealDatabaseConnection implements DatabaseConnection {
    private final String connectionString;

    /**
     * Initializes the real database connection.
     * * @param connectionString The database connection string.
     */
    public RealDatabaseConnection(String connectionString) {
        this.connectionString = connectionString;
        // In a real application, this is where the connection would be established.
        System.out.println("\n  [DB] Connecting to... " + this.connectionString);
    }

    /**
     * Fetches data from the live database.
     * * @param query The query to execute.
     * @return A list of data rows from the real database.
     */
    @Override
    public List<String> getData(String query) {
        // For demonstration purposes, we're just returning hardcoded data.
        // A real implementation would execute the query against the database
        // and return the results.
        System.out.println("  [DB] Executing query: " + query);
        return Arrays.asList("real_data_row1", "real_data_row2");
    }
} 

