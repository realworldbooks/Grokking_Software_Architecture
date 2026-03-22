package com.grokkingsoftwarearchitecture.chapter02.performance;

import java.util.HashMap;
import java.util.Map;

public class DashboardBefore {
    private final DatabaseService databaseService = new DatabaseService();

    public Map<String, String> getDashboardSummary(String userId) {
        // Brute Force. Do the slow work every single time...
        String profile = databaseService.getProfile(userId);
        String orders = databaseService.getOrders(userId);
        String activity = databaseService.getActivity(userId);

        Map<String, String> dashboardData = new HashMap<>();
        dashboardData.put("profile", profile);
        dashboardData.put("orders", orders);
        dashboardData.put("activity", activity);

        return dashboardData;
    }
}