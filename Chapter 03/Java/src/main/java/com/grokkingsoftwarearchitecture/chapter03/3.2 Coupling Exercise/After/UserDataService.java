package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

public class UserDataService {
    public UserReportData getUserReport(int userId) {
        System.out.println("    [Service] Building chunky report payload internally...");
        return new UserReportData("Jane Doe", "jane.doe@example.com", 199.90);
    }
}