using System.Collections.Generic;

namespace Chapter03.CouplingTest.Before;

public class UserReportGenerator
{
    private readonly UserDataService _dataService = new();

    public string GenerateReport(int userId)
    {
        // High Coupling: The client has to orchestrate all the pieces
        string name = _dataService.GetUserName(userId);
        string email = _dataService.GetUserEmail(userId);
        List<string> orders = _dataService.GetUserOrderIds(userId); 

        decimal totalSpent = 0;
        foreach (var orderId in orders)
        {
            totalSpent += _dataService.GetOrderTotal(orderId);
        }

        return $"User Report for {name} ({email}) - Total Spent: ${totalSpent}";
    }
}