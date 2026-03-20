using System;

namespace Chapter02.Performance;

class Program
{
    static void Main()
    {
        Console.WriteLine("=== Chapter 2: Performance Example ===\n");

        Console.WriteLine("--- Running Before: Brute Force Query ---");
        var dashboardBefore = new DashboardBefore();
        dashboardBefore.GetDashboardSummary("User123");

        Console.WriteLine("\n--- Running After: Smart Cache Architecture ---");
        var dashboardAfter = new DashboardAfter();

        Console.WriteLine("\n[Call 1: User logs in for the first time]");
        dashboardAfter.GetDashboardSummary("User999");

        Console.WriteLine("\n[Call 2: User refreshes the page a minute later]");
        dashboardAfter.GetDashboardSummary("User999");

        Console.WriteLine("\n======================================");
    }
}