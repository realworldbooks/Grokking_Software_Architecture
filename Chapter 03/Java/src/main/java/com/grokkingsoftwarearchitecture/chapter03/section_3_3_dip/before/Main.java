package com.grokkingsoftwarearchitecture.chapter03.section_3_3_dip.before;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: DIP (BEFORE) ===");
        System.out.println("The Coach is tightly coupled to concrete players.\n");

        Coach coach = new Coach();
        coach.executeGamePlan();

        System.out.println("\n===============================\n");
    }
}