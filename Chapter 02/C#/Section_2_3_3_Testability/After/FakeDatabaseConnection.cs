using System.Collections.Generic;

namespace Chapter02.Testability.After;

/// <summary>
/// This is a "Fake" or "Mock" implementation of our database interface.
/// It's a "Test Double," a stand-in for the real thing.
/// Its purpose is to be used exclusively in a testing context. It doesn't connect
/// to any real database; it just returns predictable, hardcoded data that we
/// can use to verify the behavior of the class we are testing (`ReportGenerator`).
/// </summary>
public class FakeDatabaseConnection : IDatabaseConnection
{
    public List<string> GetData(string query)
    {
        // For our test, we'll just return a list with a known number of items.
        return new List<string> { "fake_row1", "fake_row2", "fake_row3" };
    }
}