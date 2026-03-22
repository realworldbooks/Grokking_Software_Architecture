namespace Chapter03.CouplingTest.After;

/// <summary>
/// Generates a report for a user.
/// This class is an example of **Low Coupling**.
/// </summary>
public class UserReportGenerator
{
    private readonly UserDataService _dataService = new();

    public string GenerateReport(int userId)
    {
        // IMPROVEMENT: Low Coupling and "Chunky" Communication.
        // The generator is now "less smart." It doesn't know how to build the
        // report data; it just knows that it *needs* user report data.
        // It makes a single call to the service to get a complete, ready-to-use
        // data object (DTO).
        var reportData = _dataService.GetUserReport(userId);
        
        // This class is now only responsible for formatting the report, which is
        // its core concern. The business logic of *how* to calculate the total
        // spent has been moved into the service, where it belongs.
        
        // Because of the low coupling, this class is robust. The UserDataService
        // could completely change its internal implementation (e.g., change database
        // schemas, add caching), and as long as it still returns a `UserReportData`
        // object, this generator class would not need to change at all.
        return $"User Report for {reportData.Name} ({reportData.Email}) - Total Spent: ${reportData.TotalSpent}";
    }
}