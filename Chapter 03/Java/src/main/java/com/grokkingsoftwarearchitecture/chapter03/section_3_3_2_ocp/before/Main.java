package com.grokkingsoftwarearchitecture.chapter03.ocp.before;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: OCP (BEFORE) ===");
        System.out.println("Midfielder uses hardcoded if/else logic for plays.\n");

        Midfielder midfielder = new Midfielder();
        midfielder.executePlay("DribblePastOpponent");
        midfielder.executePlay("DefensiveFormation");
        midfielder.executePlay("PassToStriker"); // Fails!

        System.out.println("\n===============================\n");
    }
}