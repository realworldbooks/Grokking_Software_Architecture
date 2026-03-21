namespace Chapter02.Performance;

/// <summary>
/// Represents a dashboard service that fetches data directly from the database.
/// This class demonstrates a performance-unaware implementation.
/// </summary>
public class DashboardBefore
{
    private readonly DatabaseService _databaseService = new();

    /// <summary>
    /// Gets a summary of dashboard data for a user.
    /// </summary>
    /// <param name="userId">The ID of the user.</param>
    /// <returns>An object containing the user's dashboard data.</returns>
    public object GetDashboardSummary(string userId)
    {
        // PROBLEM: Poor Performance due to Expensive, Repetitive Calls
        // This method fetches all the required data directly from the database
        // every single time it is called.
        //
        // WHY IS THIS BAD FOR PERFORMANCE?
        // 1. High Latency: Network calls and database queries are slow. If this
        //    endpoint is hit frequently, the user will experience significant delays.
        //    In this example, each call takes 500ms, for a total of 1500ms per request.
        // 2. High Database Load: Calling the database for the same data repeatedly
        //    puts unnecessary strain on the database server, which can affect the
        //    performance of the entire application.
        // 3. Not Scalable: As the number of users and requests grows, the database
        //    will quickly become a bottleneck, and the system will not be able to scale.
        var profile = _databaseService.GetProfile(userId);
        var orders = _databaseService.GetOrders(userId);
        var activity = _databaseService.GetActivity(userId);

        return new { profile, orders, activity };
    }
}