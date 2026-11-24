using System;
using System.Collections.Generic;

namespace Chapter03.CouplingTest.Before;

/// <summary>
/// A service that provides user data.
/// This version demonstrates a "chatty," fine-grained API that leads to high coupling.
/// </summary>
public class UserDataService
{
    // PROBLEM: This service's API is too fine-grained.
    // It forces any client that wants to build a user report to know exactly
    // which methods to call and in what order. The client has to do the work
    // of orchestrating the data retrieval. This exposes the internal structure
    // of the data and creates a tight coupling with any client that uses it.
    
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