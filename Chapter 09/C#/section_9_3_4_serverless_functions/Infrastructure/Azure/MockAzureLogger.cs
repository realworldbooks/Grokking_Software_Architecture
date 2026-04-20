using System;

namespace Chapter09.ServerlessFunctions.Infrastructure.Azure;

/**
 * THE INFRASTRUCTURE CONTRACT (Azure Logger):
 * * TEACHING NOTE:
 * Azure Functions heavily utilize Dependency Injection (DI) to provide logging.
 * This mocks the 'ILogger' interface provided by the Microsoft.Extensions.Logging 
 * package. It demonstrates how a cloud provider "injects" infrastructure 
 * dependencies directly into your logic.
 */
public class MockAzureLogger
{
    /**
     * Simulates the standard LogInformation method found in production Azure apps.
     */
    public void LogInformation(string message)
    {
        // In the lab, we route cloud logs to the standard console for visibility.
        Console.WriteLine($"      [Azure Host Internal] LOG: {message}");
    }
}