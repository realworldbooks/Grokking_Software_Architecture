namespace Chapter11.Hardcoded;

/// <summary>
/// MOCK DATABASE CONNECTION - FOR DEMONSTRATION ONLY
/// 
/// This simulates a database connection to show the hardcoded credentials problem.
/// In reality, this would be NpgsqlConnection or similar.
/// </summary>
public class MockDatabaseConnection
{
    public string Host { get; }
    public string Database { get; }
    public string User { get; }
    public string Password { get; }
    private bool _isConnected;

    public MockDatabaseConnection(string host, string database, string user, string password)
    {
        Host = host;
        Database = database;
        User = user;
        Password = password;
        _isConnected = false;
    }

    public bool Connect()
    {
        // Simulate connection logic
        if (User == "admin" && Password == "Password123!")
        {
            _isConnected = true;
            return true;
        }
        else
        {
            throw new InvalidOperationException($"Authentication failed for user {User}");
        }
    }

    public List<Dictionary<string, object>> ExecuteQuery(string query)
    {
        if (!_isConnected)
        {
            throw new InvalidOperationException("Not connected to database");
        }

        // Return mock data
        return new List<Dictionary<string, object>>
        {
            new Dictionary<string, object>
            {
                { "order_id", "ORD-001" },
                { "customer_id", "CUST-123" },
                { "total", 99.99 }
            }
        };
    }

    public void Close()
    {
        _isConnected = false;
    }
}