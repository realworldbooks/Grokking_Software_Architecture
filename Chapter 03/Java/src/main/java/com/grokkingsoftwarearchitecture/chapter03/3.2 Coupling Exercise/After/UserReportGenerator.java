package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

public class UserReportGenerator {
    private final UserDataService dataService = new UserDataService();

    public String generateReport(int userId) {
        UserReportData report = dataService.getUserReport(userId);
        return String.format("User Report for %s (%s) - Total Spent: $%.2f", report.name, report.email, report.totalSpent);
    }
}