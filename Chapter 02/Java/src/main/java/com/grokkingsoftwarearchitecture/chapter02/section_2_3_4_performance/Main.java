package com.grokkingsoftwarearchitecture.chapter02.performance;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 2: Performance Example ===\n");

        System.out.println("--- Running Before: Brute Force Query ---");
        System.out.println("[Call: User logs in]");
        DashboardBefore dashboardBefore = new DashboardBefore();
        dashboardBefore.getDashboardSummary("User123");

        System.out.println("\n--- Running After: Smart Cache Architecture ---");
        DashboardAfter dashboardAfter = new DashboardAfter();

        System.out.println("\n[Call 1: User logs in for the first time]");
        dashboardAfter.getDashboardSummary("User999");

        System.out.println("\n[Call 2: User refreshes the page a minute later]");
        dashboardAfter.getDashboardSummary("User999");
        
        System.out.println("\n======================================");
    }
}