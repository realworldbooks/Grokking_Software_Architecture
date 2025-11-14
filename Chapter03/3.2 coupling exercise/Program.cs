using System;
using System.Collections.Generic;

// --- The "Service" Class ---
public class UserDataService
{
    public string GetUserName(int userId) { return "Jane Doe"; }
    public string GetUserEmail(int userId) { return "jane.doe@example.com"; }
    public List<string> GetUserOrderIds(int userId) { return new List<string> { "A123", "B456" }; }
    public decimal GetOrderTotal(string orderId) { return 99.95m; }
}

// --- The "Client" Class ---
public class UserReportGenerator
{
    private readonly UserDataService _dataService = new UserDataService();
    public string GenerateReport(int userId)
    {
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

// --- Runnable ---
public class Program
{
    public static void Main(string[] args)
    {
        var reportGenerator = new UserReportGenerator();
        string report = reportGenerator.GenerateReport(1);
        Console.WriteLine(report);
    }
}