package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability.after;

public class Demo {
    public static void run() {
        System.out.println("--- Testability Example: Dependency Injection (AFTER) ---");
        System.out.println("\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]");
        System.out.println("Unit testing the 'ReportGenerator' class with a mock database...");

        // Here is the magic of Dependency Injection.
        // We create an instance of our `FakeDatabaseConnection`.
        FakeDatabaseConnection fakeDb = new FakeDatabaseConnection();
        
        // Then, we "inject" this fake object into the constructor of our `ReportGenerator`.
        // The generator doesn't know or care that it's a fake; it only knows it's something
        // that satisfies the `IDatabaseConnection` contract.
        ReportGenerator generator = new ReportGenerator(fakeDb);
        
        // We run the same logic.
        String result = generator.generate("Sales Report");
        
        // Our fake database returns 3 rows, so our test assertion will now pass.
        // This is a true unit test: it's fast, reliable, and has no external dependencies.
        // We have successfully tested the `ReportGenerator` logic in complete isolation.
        String expected = "Report 'Sales Report' generated with 3 rows.";
        System.out.println("  > Verifying the generated report...");
        
        if (result.equals(expected)) {
            System.out.println("  ✅ TEST PASSED! Received expected result: \"" + result + "\"");
        }
        System.out.println("--------------------------------------------------\n");
    }
}