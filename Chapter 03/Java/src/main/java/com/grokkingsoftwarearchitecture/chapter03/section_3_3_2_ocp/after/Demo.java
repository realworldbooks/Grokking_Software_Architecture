package com.grokkingsoftwarearchitecture.chapter03.section_3_3_2_ocp.after;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: OCP (AFTER) ===");
        System.out.println("Midfielder accepts any class implementing Play!\n");

        Midfielder midfielder = new Midfielder();
        
        midfielder.executePlay(new DribblePastOpponent());
        midfielder.executePlay(new DefensiveFormation());
        midfielder.executePlay(new PassToStriker()); // Success!

        System.out.println("\n===============================\n");
    }
}