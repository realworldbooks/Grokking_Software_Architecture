namespace Chapter05.ServerMonitor.After.Core.Domain
{
    /// <summary>
    /// Domain Constants.
    /// Acts as the 'Single Source of Truth' for business rules, 
    /// preventing 'Magic Numbers' from being scattered throughout the code.
    /// </summary>
    public static class Constants
    {
        // Global threshold for server temperature alerts
        public const int HIGH_TEMP_THRESHOLD = 95;
    }
}