package com.grokkingsoftwarearchitecture.chapter03.section_3_3_5_dip.before;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: DIP (BEFORE) ===");
        System.out.println("The Coach is tightly coupled to concrete players.\n");

        Coach coach = new Coach();
        coach.executeGamePlan();

        System.out.println("\n===============================\n");
    }
}