using System;
using System.Collections.Generic;

namespace Chapter02.Testability;

// This is a "Fake" or "Mock" implementation of our database interface.
// It's a "Test Double," a stand-in for the real thing.
// Its purpose is to be used exclusively in a testing context. It doesn't connect
// to any real database; it just returns predictable, hardcoded data that we
// can use to verify the behavior of the class we are testing (`ReportGeneratorAfter`).
public class FakeDatabaseConnection : IDatabaseConnection
{
    public List<string> GetData(string query)
    {
        // For our test, we'll just return a list with a known number of items.
        return new List<string> { "fake_row1", "fake_row2", "fake_row3" };
    }
}

public static class TestabilityDemo
{
    public static void Run()
    {
        Console.WriteLine("--- Testability Example: Dependency Injection ---");

        // --- SCENARIO 1: The "Before" Case (Tightly Coupled) ---
        Console.WriteLine("\n[SCENARIO 1: Before Refactor - Tightly Coupled]");
        Console.WriteLine("Attempting to unit test the 'ReportGeneratorBefore' class...");
        
        // We instantiate the class. Notice its constructor immediately creates
        // a `RealDatabaseConnection`. We have no way to stop this.
        var generatorBefore = new ReportGeneratorBefore();
        var resultBefore = generatorBefore.Generate("Sales Report");

        // The `RealDatabaseConnection` returns 2 rows.
        // Our test expects 3 rows.
        // This test will therefore fail. More importantly, we are forced to run
        // the test against the `RealDatabaseConnection`, making this an
        // integration test, not a true unit test. It's slow and depends on an
        // external system (the "database").
        var expectedBefore = "Report 'Sales Report' generated with 3 rows.";
        Console.WriteLine("  > Verifying the generated report...");
        if (resultBefore != expectedBefore)
        {
            Console.WriteLine("  ❌ TEST FAILED!");
            Console.WriteLine($"     Expected: \"{expectedBefore}\"");
            Console.WriteLine($"     Received: \"{resultBefore}\"");
            Console.WriteLine("     (This fails because the hardcoded RealDatabaseConnection returns 2 rows, but our test expected 3.)");
        }

        // --- SCENARIO 2: The "After" Case (Loosely Coupled) ---
        Console.WriteLine("\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]");
        Console.WriteLine("Unit testing the 'ReportGeneratorAfter' class with a mock database...");

        // Here is the magic of Dependency Injection.
        // We create an instance of our `FakeDatabaseConnection`.
        var fakeDb = new FakeDatabaseConnection();
        
        // Then, we "inject" this fake object into the constructor of our `ReportGeneratorAfter`.
        // The generator doesn't know or care that it's a fake; it only knows it's something
        // that satisfies the `IDatabaseConnection` contract.
        var generatorAfter = new ReportGeneratorAfter(fakeDb);
        
        // We run the same logic.
        var resultAfter = generatorAfter.Generate("Sales Report");
        
        // Our fake database returns 3 rows, so our test assertion will now pass.
        // This is a true unit test: it's fast, reliable, and has no external dependencies.
        // We have successfully tested the `ReportGeneratorAfter` logic in complete isolation.
        var expectedAfter = "Report 'Sales Report' generated with 3 rows.";
        Console.WriteLine("  > Verifying the generated report...");
        if (resultAfter == expectedAfter)
        {
            Console.WriteLine($"  ✅ TEST PASSED! Received expected result: \"{resultAfter}\"");
        }
        Console.WriteLine("--------------------------------------------------\n");
    }
}