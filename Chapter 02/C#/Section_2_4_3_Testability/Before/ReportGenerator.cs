using System;

namespace Chapter02.Testability.Before;

/// <summary>
/// Demonstrates a class that is difficult to test due to tight coupling.
/// </summary>
public class ReportGenerator
{
    // This private field holds a direct reference to a concrete implementation.
    private readonly RealDatabaseConnection _dbConnection;

    public ReportGenerator()
    {
        // PROBLEM: Hardcoded Dependency (Tight Coupling)
        // The constructor creates its own instance of `RealDatabaseConnection`.
        // This is called "tight coupling." The `ReportGenerator` class is
        // permanently and directly tied to the `RealDatabaseConnection` class.
        //
        // WHY IS THIS BAD FOR TESTABILITY?
        // 1. No Isolation: You cannot test `ReportGenerator` without also
        //    testing `RealDatabaseConnection`.
        // 2. Real External Services: Unit tests should be fast and self-contained.
        //    Because we are forced to use `RealDatabaseConnection`, our tests would
        //    need to connect to an actual database. This is slow, unreliable, and
        //    can have side effects.
        // 3. No "Fakes" or "Mocks": We can't substitute a "fake" or "mock" database
        //    connection for testing purposes. For example, we can't test how the
        //    generator behaves if the database returns an error or empty data.
        _dbConnection = new RealDatabaseConnection("live_connection_string");
    }

    /// <summary>
    /// Generates a report using data from the database.
    /// </summary>
    /// <param name="reportName">The name of the report to generate.</param>
    /// <returns>A string representing the generated report.</returns>
    public string Generate(string reportName)
    {
        // This method's logic is dependent on the concrete `RealDatabaseConnection`.
        var data = _dbConnection.GetData(reportName);
        return $"Report '{reportName}' generated with {data.Count} rows.";
    }
}

public class Demo
{
    public static void Run()
    {
        Console.WriteLine("--- Testability Example: Dependency Injection (BEFORE) ---");
        Console.WriteLine("\n[SCENARIO 1: Before Refactor - Tightly Coupled]");
        Console.WriteLine("Attempting to unit test the 'ReportGenerator' class...");
        
        // We instantiate the class. Notice its constructor immediately creates
        // a `RealDatabaseConnection`. We have no way to stop this.
        var generator = new ReportGenerator();
        var result = generator.Generate("Sales Report");

        // The `RealDatabaseConnection` returns 2 rows.
        // Our test expects 3 rows.
        // This test will therefore fail. More importantly, we are forced to run
        // the test against the `RealDatabaseConnection`, making this an
        // integration test, not a true unit test. It's slow and depends on an
        // external system (the "database").
        var expected = "Report 'Sales Report' generated with 3 rows.";
        Console.WriteLine("  > Verifying the generated report...");
        
        if (result != expected)
        {
            Console.WriteLine("  ❌ TEST FAILED!");
            Console.WriteLine($"     Expected: \"{expected}\"");
            Console.WriteLine($"     Received: \"{result}\"");
            Console.WriteLine("     (This fails because the hardcoded RealDatabaseConnection returns 2 rows, but our test expected 3.)");
        }
        Console.WriteLine("--------------------------------------------------\n");
    }
}