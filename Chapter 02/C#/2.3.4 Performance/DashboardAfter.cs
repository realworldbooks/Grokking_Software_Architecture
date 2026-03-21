namespace Chapter02.Performance;

/// <summary>
/// Represents a dashboard service that uses a cache to improve performance.
/// This class demonstrates the "Cache-Aside" pattern.
/// </summary>
public class DashboardAfter
{
    // Using a constant for the cache's Time-To-Live (TTL) is a good practice.
    // It makes the code more readable and ensures the expiration policy is consistent.
    private const int CACHE_TTL_SECONDS = 600; // 10 minutes
    
    private readonly DatabaseService _databaseService = new();
    private readonly CacheService _cache = new();

    /// <summary>
    /// Gets a summary of dashboard data for a user, using a cache to optimize performance.
    /// </summary>
    /// <param name="userId">The ID of the user.</param>
    /// <returns>An object containing the user's dashboard data.</returns>
    public object GetDashboardSummary(string userId)
    {
        string cacheKey = $"dashboard:{userId}";

        // IMPROVEMENT: The "Cache-Aside" Pattern
        //
        // STEP 1: Check the cache first.
        // Before doing any expensive work, we check if the data we need is already
        // in our fast in-memory cache. A cache read is significantly faster (e.g., <5ms)
        // than a database query (e.g., 500ms).
        var cachedDashboard = _cache.Get(cacheKey);
        
        // If `cachedDashboard` is not null, we have a "cache hit."
        // We can immediately return the cached data without touching the database.
        if (cachedDashboard != null)
        {
            return cachedDashboard;
        }

        // STEP 2: Handle a "cache miss."
        // If the data is not in the cache, we proceed with the expensive operation:
        // fetching the data from the database.
        var profile = _databaseService.GetProfile(userId);
        var orders = _databaseService.GetOrders(userId);
        var activity = _databaseService.GetActivity(userId);

        var dashboardData = new { profile, orders, activity };

        // STEP 3: Store the result in the cache.
        // Before returning the data, we save it to the cache. The next time this
        // method is called for the same user (within the TTL window), we'll get a
        // cache hit and avoid the database calls altogether.
        _cache.Set(cacheKey, dashboardData, CACHE_TTL_SECONDS);

        return dashboardData;
    }
}