using System;

namespace Chapter03.CouplingTest.After;

public class UserDataService
{
    public UserReportData GetUserReport(int userId) 
    { 
        Console.WriteLine("    [Service] Building chunky report payload internally...");
        
        // The service orchestrates its own data now
        return new UserReportData 
        { 
            Name = "Jane Doe",
            Email = "jane.doe@example.com",
            TotalSpent = 199.90m // (99.95 * 2 orders)
        };
    }
}