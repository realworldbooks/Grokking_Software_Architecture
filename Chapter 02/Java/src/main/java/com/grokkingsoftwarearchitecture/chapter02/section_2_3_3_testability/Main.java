package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 2: Testability Example ===");

        System.out.println("\n--- Running Before: Tightly Coupled Test ---");
        ReportGeneratorBefore generatorBefore = new ReportGeneratorBefore();
        String resultBefore = generatorBefore.generate("FailingTest");
        String expectedBefore = "Report 'FailingTest' generated with 3 rows.";

        if (!resultBefore.equals(expectedBefore)) {
            System.out.println("❌ TEST FAILED!");
            System.out.println("  Expected: \"" + expectedBefore + "\"");
            System.out.println("  Received: \"" + resultBefore + "\"");
        }

        System.out.println("\n--- Running After: Loosely Coupled Test ---");
        
        DatabaseConnection fakeDb = new DatabaseConnection() {
            public List<String> getData(String query) {
                return Arrays.asList("row1", "row2", "row3");
            }
        };

        ReportGeneratorAfter generatorAfter = new ReportGeneratorAfter(fakeDb);
        String resultAfter = generatorAfter.generate("PassingTest");
        String expectedAfter = "Report 'PassingTest' generated with 3 rows.";

        if (resultAfter.equals(expectedAfter)) {
            System.out.println("✅ TEST PASSED! Received expected result: \"" + resultAfter + "\"");
        }
        System.out.println("\n======================================");
    }
}