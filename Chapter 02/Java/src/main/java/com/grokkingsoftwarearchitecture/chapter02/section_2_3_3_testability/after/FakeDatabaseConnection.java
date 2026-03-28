package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability.after;

import java.util.Arrays;
import java.util.List;

/**
 * This is a "Fake" or "Mock" implementation of our database interface.
 * It's a "Test Double," a stand-in for the real thing.
 * Its purpose is to be used exclusively in a testing context. It doesn't connect
 * to any real database; it just returns predictable, hardcoded data that we
 * can use to verify the behavior of the class we are testing (`ReportGenerator`).
 */
public class FakeDatabaseConnection implements DatabaseConnection {
    @Override
    public List<String> getData(String query) {
        // For our test, we'll just return a list with a known number of items.
        return Arrays.asList("fake_row1", "fake_row2", "fake_row3");
    }
}