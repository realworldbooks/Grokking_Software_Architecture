package com.grokkingsoftwarearchitecture.chapter03.section_3_3_4_isp.before;

public class Demo {
    public static void run() {
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