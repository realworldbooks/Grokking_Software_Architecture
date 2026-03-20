namespace Chapter02.Performance;

public class DashboardBefore
{
    private readonly DatabaseService _databaseService = new();

    public object GetDashboardSummary(string userId)
    {
        var profile = _databaseService.GetProfile(userId);
        var orders = _databaseService.GetOrders(userId);
        var activity = _databaseService.GetActivity(userId);

        return new { profile, orders, activity };
    }
}