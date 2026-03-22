namespace Chapter02.Testability;

/// <summary>
/// Demonstrates a class that is easy to test by using Dependency Injection.
/// </summary>
public class ReportGeneratorAfter
{
    // The class depends on an abstraction (an interface), not a concrete class.
    private readonly IDatabaseConnection _dbConnection;

    // IMPROVEMENT: Dependency is Injected (Loose Coupling)
    // Instead of creating its own dependency, the class receives it as a
    // constructor parameter. This is a common form of "Dependency Injection."
    //
    // WHY IS THIS GOOD FOR TESTABILITY?
    // 1. Loose Coupling: The `ReportGeneratorAfter` class is no longer tightly
    //    coupled to `RealDatabaseConnection`. It only knows about the `IDatabaseConnection`
    //    interface.
    // 2. Control Inversion: The control of which database connection to use has been
    //    "inverted." It's no longer the responsibility of this class; it's the
    //    responsibility of whoever *creates* this class.
    // 3. Mocking is Now Possible: In a test environment, we can create a "mock" or
    //    "fake" implementation of `IDatabaseConnection` and pass it to the constructor.
    //    This allows us to test the `ReportGeneratorAfter` in complete isolation,
    //    simulating different database scenarios (e.g., returning errors, empty data, etc.)
    //    without needing a real database.
    public ReportGeneratorAfter(IDatabaseConnection dbConnection)
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