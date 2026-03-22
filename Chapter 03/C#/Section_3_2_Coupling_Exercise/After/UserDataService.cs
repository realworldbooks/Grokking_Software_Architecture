using System;

namespace Chapter03.CouplingTest.After;

/// <summary>
/// A service that provides user data.
/// This version demonstrates a "chunky," coarse-grained API that leads to low coupling.
/// </summary>
public class UserDataService
{
    /// <summary>
    /// Gets a complete data package for a user report.
    /// </summary>
    /// <param name="userId">The user to generate the report for.</param>
    /// <returns>A UserReportData object containing all necessary information.</returns>
    public UserReportData GetUserReport(int userId) 
    { 
        Console.WriteLine("    [Service] Building chunky report payload internally...");
        
        // IMPROVEMENT: The service is now responsible for its own logic.
        // It performs all the necessary steps internally to construct the final
        // data object. It no longer exposes its internal, fine-grained methods
        // to the outside world. This is a much better service boundary.
        // If the logic for calculating `TotalSpent` changes, it only changes here.
        return new UserReportData 
        { 
            Name = "Jane Doe",
            Email = "jane.doe@example.com",
            TotalSpent = 199.90m // (This would be calculated from internal data sources)
        };
    }
}