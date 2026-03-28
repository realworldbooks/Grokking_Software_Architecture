using System;

namespace Chapter02.Testability.After;

/// <summary>
/// Demonstrates a class that is easy to test by using Dependency Injection.
/// </summary>
public class ReportGenerator
{
    // The class depends on an abstraction (an interface), not a concrete class.
    private readonly IDatabaseConnection _dbConnection;

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
    public ReportGenerator(IDatabaseConnection dbConnection)
    {
        _dbConnection = dbConnection;
    }

    /// <summary>
    /// Generates a report using data from the injected database connection.
    /// </summary>
    /// <param name="reportName">The name of the report to generate.</param>
    /// <returns>A string representing the generated report.</returns>
    public string Generate(string reportName)
    {
        var data = _dbConnection.GetData(reportName);
        return $"Report '{reportName}' generated with {data.Count} rows.";
    }
}

public class Demo
{
    public static void Run()
    {
        Console.WriteLine("--- Testability Example: Dependency Injection (AFTER) ---");
        Console.WriteLine("\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]");
        Console.WriteLine("Unit testing the 'ReportGenerator' class with a mock database...");

        // Here is the magic of Dependency Injection.
        // We create an instance of our `FakeDatabaseConnection`.
        var fakeDb = new FakeDatabaseConnection();
        
        // Then, we "inject" this fake object into the constructor of our `ReportGenerator`.
        // The generator doesn't know or care that it's a fake; it only knows it's something
        // that satisfies the `IDatabaseConnection` contract.
        var generator = new ReportGenerator(fakeDb);
        
        // We run the same logic.
        var result = generator.Generate("Sales Report");
        
        // Our fake database returns 3 rows, so our test assertion will now pass.
        // This is a true unit test: it's fast, reliable, and has no external dependencies.
        // We have successfully tested the `ReportGenerator` logic in complete isolation.
        var expected = "Report 'Sales Report' generated with 3 rows.";
        Console.WriteLine("  > Verifying the generated report...");
        
        if (result == expected)
        {
            Console.WriteLine($"  ✅ TEST PASSED! Received expected result: \"{result}\"");
        }
        Console.WriteLine("--------------------------------------------------\n");
    }
}