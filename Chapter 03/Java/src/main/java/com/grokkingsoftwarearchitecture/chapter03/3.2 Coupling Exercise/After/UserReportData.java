package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

public class UserReportData {
    public final String name;
    public final String email;
    public final double totalSpent;

    public UserReportData(String name, String email, double totalSpent) {
        this.name = name;
        this.email = email;
        this.totalSpent = totalSpent;
    }
}