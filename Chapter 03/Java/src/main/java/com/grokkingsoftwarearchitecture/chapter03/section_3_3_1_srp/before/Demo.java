package com.grokkingsoftwarearchitecture.chapter03.section_3_3_1_srp.before;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: SRP (BEFORE) ===");
        System.out.println("The Player class is doing way too much work!\n");

        Player player = new Player("Alex");
        
        player.dribbleBall();
        player.determineBestPosition();
        player.saveStatsToDatabase();

        System.out.println("\n===============================\n");
    }
}