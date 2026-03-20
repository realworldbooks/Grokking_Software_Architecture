namespace Chapter02.Testability;

public class ReportGeneratorAfter
{
    private IDatabaseConnection _dbConnection;

    // Dependency is injected via the constructor!
    public ReportGeneratorAfter(IDatabaseConnection dbConnection)
    {
        _dbConnection = dbConnection;
    }

    public string Generate(string reportName)
    {
        var data = _dbConnection.GetData(reportName);
        return $"Report '{reportName}' generated with {data.Count} rows.";
    }
}