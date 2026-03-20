package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: Coupling Test (AFTER) ===");
        System.out.println("Notice how clean and 'chunky' the interaction is now!\n");

        UserReportGenerator generator = new UserReportGenerator();
        String result = generator.generateReport(1);

        System.out.println("\nRESULT: " + result);
        System.out.println("========================================\n");
    }
}