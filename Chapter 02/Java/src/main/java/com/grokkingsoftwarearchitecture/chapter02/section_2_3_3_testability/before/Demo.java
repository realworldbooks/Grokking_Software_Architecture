package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability.before;

public class Demo {
    public static void run() {
        System.out.println("--- Testability Example: Dependency Injection (BEFORE) ---");
        System.out.println("\n[SCENARIO 1: Before Refactor - Tightly Coupled]");
        System.out.println("Attempting to unit test the 'ReportGenerator' class...");
        
        // We instantiate the class. Notice its constructor immediately creates
        // a `RealDatabaseConnection`. We have no way to stop this.
        ReportGenerator generator = new ReportGenerator();
        String result = generator.generate("Sales Report");

        // The `RealDatabaseConnection` returns 2 rows.
        // Our test expects 3 rows.
        // This test will therefore fail. More importantly, we are forced to run
        // the test against the `RealDatabaseConnection`, making this an
        // integration test, not a true unit test. It's slow and depends on an
        // external system (the "database").
        String expected = "Report 'Sales Report' generated with 3 rows.";
        System.out.println("  > Verifying the generated report...");
        
        if (!result.equals(expected)) {
            System.out.println("  ❌ TEST FAILED!");
            System.out.println("     Expected: \"" + expected + "\"");
            System.out.println("     Received: \"" + result + "\"");
            System.out.println("     (This fails because the hardcoded RealDatabaseConnection returns 2 rows, but our test expected 3.)");
        }
        System.out.println("--------------------------------------------------\n");
    }
}