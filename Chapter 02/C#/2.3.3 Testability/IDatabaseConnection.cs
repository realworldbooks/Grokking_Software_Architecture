using System.Collections.Generic;

namespace Chapter02.Testability;

/// <summary>
/// Defines the "contract" for a database connection.
/// An interface is an abstract type that only defines the public members (methods, properties)
/// that a class MUST implement if it implements the interface.
/// 
/// By creating this contract, other classes (like `ReportGeneratorAfter`) can depend on
/// the `IDatabaseConnection` abstraction rather than a specific, concrete database class.
/// This is the key to achieving loose coupling and enabling dependency injection.
/// </summary>
public interface IDatabaseConnection
{
    /// <summary>
    /// Fetches data from the database based on a query.
    /// </summary>
    /// <param name="query">The query to execute.</param>
    /// <returns>A list of strings representing the data rows.</returns>
    List<string> GetData(string query);
}