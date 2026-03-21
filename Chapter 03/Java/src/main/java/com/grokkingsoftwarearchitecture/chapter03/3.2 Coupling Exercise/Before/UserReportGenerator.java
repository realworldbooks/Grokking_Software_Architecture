package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.before;

import java.util.List;

/**
 * ANTI-PATTERN: The Tightly Coupled Client.
 * * ARCHITECTURE PROBLEM: Because the UserDataService is too granular, this client 
 * class is forced to take on the responsibility of orchestrating the data gathering. 
 * * The generator now knows WAY too much about the internal structure of the data. 
 * It knows that it has to fetch the user, then fetch the orders, and then loop 
 * through the orders to calculate a total. This is a severe violation of encapsulation.
 */
public class UserReportGenerator {
    private final UserDataService dataService = new UserDataService();

    public String generateReport(int userId) {
        
        // 🚨 ARCHITECTURE WARNING: High temporal coupling. The client has to 
        // call these specific methods in a specific sequence to get what it needs.
        String name = dataService.getUserName(userId);
        String email = dataService.getUserEmail(userId);
        List<String> orders = dataService.getUserOrderIds(userId);

        double totalSpent = 0.0;
        
        // 🚨 ARCHITECTURE WARNING: Feature Envy / Chatty Execution.
        // The client is forced to loop through orders and request totals one by one.
        // It is doing the heavy lifting that the service should be doing for it!
        for (String orderId : orders) {
            totalSpent += dataService.getOrderTotal(orderId);
        }

        return String.format("User Report for %s (%s) - Total Spent: $%.2f", name, email, totalSpent);
    }
}