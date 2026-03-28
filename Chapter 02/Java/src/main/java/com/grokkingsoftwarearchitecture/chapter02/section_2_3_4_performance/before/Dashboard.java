package com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.before;

import com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.DatabaseService;

/**
 * Represents a dashboard service that fetches data directly from the database.
 * ARCHITECTURAL NOTE: This class demonstrates a performance-unaware implementation.
 * There is no memory layer protecting the database from repetitive queries.
 */
public class Dashboard {
    private final DatabaseService databaseService = new DatabaseService();

    /**
     * Gets a summary of dashboard data for a user.
     * * @param userId The ID of the user.
     * @return An object containing the user's dashboard data.
     */
    public DashboardData getDashboardSummary(String userId) {
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
        String profile = databaseService.getProfile(userId);
        String orders = databaseService.getOrders(userId);
        String activity = databaseService.getActivity(userId);

        return new DashboardData(profile, orders, activity);
    }

    // A simple inner class to hold the returned data, taking the place of C#'s anonymous object
    public static class DashboardData {
        public final String profile;
        public final String orders;
        public final String activity;

        public DashboardData(String profile, String orders, String activity) {
            this.profile = profile;
            this.orders = orders;
            this.activity = activity;
        }
    }
}

