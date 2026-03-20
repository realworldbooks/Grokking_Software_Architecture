package com.grokkingsoftwarearchitecture.chapter03.isp.before;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: ISP (BEFORE) ===");
        System.out.println("Midfielder is forced to implement Goalie methods!\n");

        TrainingSession player = new Midfielder();
        
        player.practiceShooting();
        player.practiceTackling();

        try {
            player.practiceDivingSaves(); // This will crash!
        } catch (Exception ex) {
            System.out.println("  [ERROR] " + ex.getMessage());
        }

        System.out.println("\n===============================\n");
    }
}