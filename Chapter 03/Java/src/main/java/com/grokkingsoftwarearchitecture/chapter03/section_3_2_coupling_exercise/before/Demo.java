package com.grokkingsoftwarearchitecture.chapter03.section_3_2_coupling_exercise.before;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: Coupling Test (BEFORE) ===");
        System.out.println("Notice how many 'chatty' calls the client has to make!\n");

        UserReportGenerator generator = new UserReportGenerator();
        String result = generator.generateReport(1);

        System.out.println("\nRESULT: " + result);
        System.out.println("=========================================\n");
    }
}