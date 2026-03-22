using System.Collections.Generic;

namespace Chapter03.CouplingTest.Before;

/// <summary>
/// Generates a report for a user.
/// This class is an example of **High Coupling**.
/// </summary>
public class UserReportGenerator
{
    private readonly UserDataService _dataService = new();

    public string GenerateReport(int userId)
    {
        // PROBLEM: High Coupling and "Chatty" Communication.
        // This report generator is "too smart." It knows the intimate details
        // of how to build a user report. It has to call multiple, fine-grained
        // methods on the data service to get individual pieces of data.
        // This creates a tight bond between this class and the UserDataService.
        string name = _dataService.GetUserName(userId);
        string email = _dataService.GetUserEmail(userId);
        List<string> orders = _dataService.GetUserOrderIds(userId); 

        // PROBLEM: Misplaced Responsibility.
        // The logic for calculating the total spent for a user lives here, inside the
        // client. What if another part of the application also needs to calculate this?
        // That logic would have to be duplicated. The data service should be responsible
        // for providing this calculation.
        decimal totalSpent = 0;
        foreach (var orderId in orders)
        {
            totalSpent += _dataService.GetOrderTotal(orderId);
        }

        // Because of the high coupling, this class is fragile. If the UserDataService
        // changes—for example, if GetUserOrderIds starts returning a different type—this
        // class will break and need to be updated.
        return $"User Report for {name} ({email}) - Total Spent: ${totalSpent}";
    }
}