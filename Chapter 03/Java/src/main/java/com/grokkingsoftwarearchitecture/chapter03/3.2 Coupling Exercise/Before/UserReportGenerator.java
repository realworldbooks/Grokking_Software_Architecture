package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.before;

import java.util.List;

public class UserReportGenerator {
    private final UserDataService dataService = new UserDataService();

    public String generateReport(int userId) {
        String name = dataService.getUserName(userId);
        String email = dataService.getUserEmail(userId);
        List<String> orders = dataService.getUserOrderIds(userId);

        double totalSpent = 0.0;
        for (String orderId : orders) {
            totalSpent += dataService.getOrderTotal(orderId);
        }

        return String.format("User Report for %s (%s) - Total Spent: $%.2f", name, email, totalSpent);
    }
}