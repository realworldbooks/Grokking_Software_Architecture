package com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.after;

public class Demo {
    public static void run() {
        System.out.println("--- Performance Example: Caching (AFTER) ---");
        System.out.println("\n[SCENARIO 2: After Refactor - With Cache-Aside Pattern]");
        
        final String USER_ID = "user123";
        Dashboard dashboard = new Dashboard();

        // First call for a user is a "cache miss". The app has to do the slow
        // work of hitting the database. This call will be slow.
        System.out.println("\n(First call for a new user... expect a cache miss)");
        long startTime1 = System.currentTimeMillis();
        dashboard.getDashboardSummary(USER_ID);
        long endTime1 = System.currentTimeMillis();
        System.out.println("\n>> Time taken: " + (endTime1 - startTime1) + "ms");

        // The user refreshes the page. The data is now in the cache.
        // This second call is a "cache hit" and will be dramatically faster
        // because it completely avoids the slow database calls.
        System.out.println("\n(Second call for the same user... expect a cache hit)");
        long startTime2 = System.currentTimeMillis();
        dashboard.getDashboardSummary(USER_ID);
        long endTime2 = System.currentTimeMillis();
        System.out.println("\n>> Time taken: " + (endTime2 - startTime2) + "ms");
        System.out.println("--------------------------------------------------\n");
    }
}
