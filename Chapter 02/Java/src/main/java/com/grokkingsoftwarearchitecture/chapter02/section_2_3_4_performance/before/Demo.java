package com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance.before;

public class Demo {
    public static void run() {
        System.out.println("--- Performance Example: Caching (BEFORE) ---");
        System.out.println("\n[SCENARIO 1: Before Refactor - No Caching]");
        System.out.println("Notice how slow this is. Every request hits the database.\n");
        
        final String USER_ID = "user123";
        Dashboard dashboard = new Dashboard();
        
        long startTime = System.currentTimeMillis();
        dashboard.getDashboardSummary(USER_ID);
        long endTime = System.currentTimeMillis();
        
        long timeTaken = endTime - startTime;
        System.out.println("\n>> Time taken: " + timeTaken + "ms");
        System.out.println("--------------------------------------------------\n");
    }
} 
