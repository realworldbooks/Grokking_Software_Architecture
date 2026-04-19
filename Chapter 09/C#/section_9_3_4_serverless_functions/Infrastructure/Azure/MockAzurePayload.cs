using System;

namespace Chapter09.Section3_ServerlessFunctions.Infrastructure;

// TEACHING NOTE:
// Azure Functions heavily utilize Dependency Injection for logging.
// This mocks the standard Microsoft.Extensions.Logging.ILogger interface.
public class MockAzureLogger
{
    public void LogInformation(string message)
    {
        Console.WriteLine(message);
    }
}