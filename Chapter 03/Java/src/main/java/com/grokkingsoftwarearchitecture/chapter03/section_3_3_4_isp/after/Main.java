package com.grokkingsoftwarearchitecture.chapter03.isp.after;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: ISP (AFTER) ===");
        System.out.println("Interfaces are segregated. No more Exceptions!\n");

        FieldPlayerTraining midfielder = new Midfielder();
        midfielder.practiceShooting();
        
        System.out.println();
        
        Goalie goalie = new Goalie();
        goalie.practiceDivingSaves();
        goalie.practiceHandDistribution();

        System.out.println("\n===============================\n");
    }
}