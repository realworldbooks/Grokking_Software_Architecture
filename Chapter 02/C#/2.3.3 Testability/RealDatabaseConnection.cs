using System;
using System.Collections.Generic;

namespace Chapter02.Testability;

public class RealDatabaseConnection : IDatabaseConnection
{
    private readonly string _connectionString;

    public RealDatabaseConnection(string connectionString)
    {
        _connectionString = connectionString;
        Console.WriteLine($"\n  [DB] Connecting to... {_connectionString}");
    }

    public List<string> GetData(string query)
    {
        return new List<string> { "real_data_row1", "real_data_row2" };
    }
}