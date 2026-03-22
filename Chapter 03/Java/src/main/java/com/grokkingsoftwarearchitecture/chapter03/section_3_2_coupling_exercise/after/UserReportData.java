package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

/**
 * THE SOLUTION: The Data Transfer Object (DTO).
 * * ARCHITECTURE NOTE: This class acts as a highly cohesive "chunky" payload. 
 * Instead of passing individual strings and primitives back and forth, we 
 * package everything the client needs into a single, immutable container.
 * * This drastically reduces the number of return trips needed across 
 * architectural boundaries, improving performance and establishing a clear 
 * data contract between the service and the client.
 */
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