namespace Chapter02.Testability;

/// <summary>
/// Demonstrates a class that is difficult to test due to tight coupling.
/// </summary>
public class ReportGeneratorBefore
{
    // This private field holds a direct reference to a concrete implementation.
    private readonly RealDatabaseConnection _dbConnection;

    public ReportGeneratorBefore()
    {
        // PROBLEM: Hardcoded Dependency (Tight Coupling)
        // The constructor creates its own instance of `RealDatabaseConnection`.
        // This is called "tight coupling." The `ReportGeneratorBefore` class is
        // permanently and directly tied to the `RealDatabaseConnection` class.
        //
        // WHY IS THIS BAD FOR TESTABILITY?
        // 1. No Isolation: You cannot test `ReportGeneratorBefore` without also
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