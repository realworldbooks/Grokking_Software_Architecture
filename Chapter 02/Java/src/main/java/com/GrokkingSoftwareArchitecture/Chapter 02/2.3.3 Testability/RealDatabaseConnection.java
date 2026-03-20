package com.architecturebook.chapter2.testability;

import java.util.Arrays;
import java.util.List;

public class RealDatabaseConnection implements DatabaseConnection {
    public RealDatabaseConnection(String connectionString) {
        System.out.println("\n  [DB] Connecting to... " + connectionString);
    }

    public List<String> getData(String query) {
        return Arrays.asList("real_data_row1", "real_data_row2");
    }
}