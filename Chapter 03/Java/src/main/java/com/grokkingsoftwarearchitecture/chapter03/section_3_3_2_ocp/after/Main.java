package com.grokkingsoftwarearchitecture.chapter03.ocp.after;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: OCP (AFTER) ===");
        System.out.println("Midfielder accepts any class implementing Play!\n");

        Midfielder midfielder = new Midfielder();
        
        midfielder.executePlay(new DribblePastOpponent());
        midfielder.executePlay(new DefensiveFormation());
        midfielder.executePlay(new PassToStriker()); // Success!

        System.out.println("\n===============================\n");
    }
}