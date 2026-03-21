using System;
using System.Collections.Generic;

namespace Chapter02.Testability;

/// <summary>
/// This is the "real" or "production" implementation of the `IDatabaseConnection` interface.
/// It would contain the actual logic to connect to and query a live database.
/// In a real-world application, this class would use a library like Dapper or Entity Framework Core
/// to interact with a SQL Server, PostgreSQL, or other database.
/// </summary>
public class RealDatabaseConnection : IDatabaseConnection
{
    private readonly string _connectionString;

    /// <summary>
    /// Initializes the real database connection.
    /// </summary>
    /// <param name="connectionString">The database connection string.</param>
    public RealDatabaseConnection(string connectionString)
    {
        _connectionString = connectionString;
        // In a real application, this is where the connection would be established.
        Console.WriteLine($"\n  [DB] Connecting to... {_connectionString}");
    }

    /// <summary>
    /// Fetches data from the live database.
    /// </summary>
    /// <param name="query">The query to execute.</param>
    /// <returns>A list of data rows from the real database.</returns>
    public List<string> GetData(string query)
    {
        // For demonstration purposes, we're just returning hardcoded data.
        // A real implementation would execute the query against the database
        // and return the results.
        Console.WriteLine($"  [DB] Executing query: {query}");
        return new List<string> { "real_data_row1", "real_data_row2" };
    }
}