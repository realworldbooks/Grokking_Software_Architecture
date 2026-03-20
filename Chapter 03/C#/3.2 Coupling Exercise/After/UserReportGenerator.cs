namespace Chapter03.CouplingTest.After;

public class UserReportGenerator
{
    private readonly UserDataService _dataService = new();

    public string GenerateReport(int userId)
    {
        // Low Coupling: The client just asks for what it needs once
        var report = _dataService.GetUserReport(userId);
        
        return $"User Report for {report.Name} ({report.Email}) - Total Spent: ${report.TotalSpent}";
    }
}