namespace Chapter02.Performance;

public class DashboardAfter
{
    private const int CACHE_TTL_SECONDS = 600; // 10 minutes
    
    private readonly DatabaseService _databaseService = new();
    private readonly CacheService _cache = new();

    public object GetDashboardSummary(string userId)
    {
        string cacheKey = $"dashboard:{userId}";

        // 1. Check the FAST cache first
        var cachedDashboard = _cache.Get(cacheKey);
        
        if (cachedDashboard != null)
        {
            return cachedDashboard;
        }

        // 2. Cache MISS. Do the slow work...
        var profile = _databaseService.GetProfile(userId);
        var orders = _databaseService.GetOrders(userId);
        var activity = _databaseService.GetActivity(userId);

        var dashboardData = new { profile, orders, activity };

        // 3. Save the result using our constant
        _cache.Set(cacheKey, dashboardData, CACHE_TTL_SECONDS);

        return dashboardData;
    }
}