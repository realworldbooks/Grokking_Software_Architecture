using System;

namespace Chapter02.Performance;

public class DatabaseService
{
    public string GetProfile(string id)
    {
        Console.WriteLine($"    [DB] Fetching Profile for {id}... (takes 500ms)");
        return "User_Profile_Data";
    }

    public string GetOrders(string id)
    {
        Console.WriteLine($"    [DB] Fetching Orders for {id}... (takes 500ms)");
        return "User_Orders_Data";
    }

    public string GetActivity(string id)
    {
        Console.WriteLine($"    [DB] Fetching Activity for {id}... (takes 500ms)");
        return "User_Activity_Data";
    }
}