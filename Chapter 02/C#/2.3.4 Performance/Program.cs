using System;
using System.Diagnostics;

namespace Chapter02.Performance;

public static class PerformanceDemo
{
    public static void Run()
    {
        Console.WriteLine("--- Performance Example: Caching ---");
        var sw = new Stopwatch();
        const string USER_ID = "user123";

        // --- SCENARIO 1: The "Before" Case (No Caching) ---
        Console.WriteLine("\\n[SCENARIO 1: Before Refactor - No Caching]");
        var dashboardBefore = new DashboardBefore();
        
        sw.Start();
        dashboardBefore.GetDashboardSummary(USER_ID);
        sw.Stop();
        Console.WriteLine($"\\n>> Time taken: {sw.ElapsedMilliseconds}ms");


        // --- SCENARIO 2: The "After" Case (With Caching) ---
        Console.WriteLine("\\n[SCENARIO 2: After Refactor - With Cache-Aside Pattern]");
        var dashboardAfter = new DashboardAfter();

        // First call for a user is a "cache miss". The app has to do the slow
        // work of hitting the database. This call will be slow.
        Console.WriteLine("\\n(First call for a new user... expect a cache miss)");
        sw.Restart();
        dashboardAfter.GetDashboardSummary(USER_ID);
        sw.Stop();
        Console.WriteLine($"\\n>> Time taken: {sw.ElapsedMilliseconds}ms");

        // The user refreshes the page. The data is now in the cache.
        // This second call is a "cache hit" and will be dramatically faster
        // because it completely avoids the slow database calls.
        Console.WriteLine("\\n(Second call for the same user... expect a cache hit)");
        sw.Restart();
        dashboardAfter.GetDashboardSummary(USER_ID);
        sw.Stop();
        Console.WriteLine($"\\n>> Time taken: {sw.ElapsedMilliseconds}ms");
        Console.WriteLine("--------------------------------------\\n");
    }
}