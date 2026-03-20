namespace Chapter02.Testability;

public class ReportGeneratorBefore
{
    private RealDatabaseConnection _dbConnection;

    public ReportGeneratorBefore()
    {
        // Hardcoded dependency!
        _dbConnection = new RealDatabaseConnection("live_connection_string");
    }

    public string Generate(string reportName)
    {
        var data = _dbConnection.GetData(reportName);
        return $"Report '{reportName}' generated with {data.Count} rows.";
    }
}