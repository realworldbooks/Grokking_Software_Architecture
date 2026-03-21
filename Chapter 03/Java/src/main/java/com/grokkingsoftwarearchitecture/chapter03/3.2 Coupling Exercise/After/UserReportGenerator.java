package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

/**
 * THE SOLUTION: The Loosely Coupled Client.
 * * ARCHITECTURE NOTE: Look at how clean and simple the client has become.
 * It no longer suffers from "Feature Envy"—it doesn't have to loop through 
 * orders, request individual totals, or perform its own math.
 * * It simply asks the service for the completed report data and formats it. 
 * If the underlying database schema or the way a "Total" is calculated changes, 
 * this client class will not require a single line of code to be modified.
 */
public class UserReportGenerator {
    private final UserDataService dataService = new UserDataService();

    public String generateReport(int userId) {
        
        // A single, clean call replaces multiple chatty calls.
        UserReportData report = dataService.getUserReport(userId);
        
        return String.format("User Report for %s (%s) - Total Spent: $%.2f", 
                             report.name, report.email, report.totalSpent);
    }
}