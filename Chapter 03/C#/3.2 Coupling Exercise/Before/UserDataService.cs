using System;
using System.Collections.Generic;

namespace Chapter03.CouplingTest.Before;

public class UserDataService
{
    public string GetUserName(int userId) 
    {
        Console.WriteLine("    [Service] Fetching Name...");
        return "Jane Doe"; 
    }
    
    public string GetUserEmail(int userId) 
    { 
        Console.WriteLine("    [Service] Fetching Email...");
        return "jane.doe@example.com"; 
    }
    
    public List<string> GetUserOrderIds(int userId) 
    { 
        Console.WriteLine("    [Service] Fetching Order IDs...");
        return new List<string> { "A123", "B456" }; 
    }
    
    public decimal GetOrderTotal(string orderId) 
    { 
        Console.WriteLine($"    [Service] Fetching Total for Order {orderId}...");
        return 99.95m; 
    }
}