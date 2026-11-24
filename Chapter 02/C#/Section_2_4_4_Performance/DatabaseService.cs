using System;
using System.Threading;

namespace Chapter02.Performance;

/// <summary>
/// Simulates a slow, expensive database service.
/// In a real application, these methods would contain logic to query a database.
/// 
/// ARCHITECTURAL NOTE: In this "Before" state, every single request to the 
/// dashboard will be forced to wait for these slow network calls to finish.
/// </summary>
public class DatabaseService
{
    private void SimulateNetworkLatency()
    {
        // This is used to simulate the real-world latency of network I/O
        // and database query execution time.
        Thread.Sleep(500);
    }

    /// <summary>
    /// Simulates fetching a user profile from the database.
    /// </summary>
    public string GetProfile(string id)
    {
        Console.WriteLine($"    [DB] Fetching Profile for {id}...");
        SimulateNetworkLatency();
        Console.WriteLine("    [DB] >> Profile data received.");
        return "User_Profile_Data";
    }

    /// <summary>
    /// Simulates fetching a user's orders from the database.
    /// </summary>
    public string GetOrders(string id)
    {
        Console.WriteLine($"    [DB] Fetching Orders for {id}...");
        SimulateNetworkLatency();
        Console.WriteLine("    [DB] >> Order data received.");
        return "User_Orders_Data";
    }

    /// <summary>
    /// Simulates fetching a user's activity from the database.
    /// </summary>
    public string GetActivity(string id)
    {
        Console.WriteLine($"    [DB] Fetching Activity for {id}...");
        SimulateNetworkLatency();
        Console.WriteLine("    [DB] >> Activity data received.");
        return "User_Activity_Data";
    }
}