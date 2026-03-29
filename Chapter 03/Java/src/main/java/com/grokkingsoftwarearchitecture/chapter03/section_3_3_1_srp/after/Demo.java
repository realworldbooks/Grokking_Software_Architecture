package com.grokkingsoftwarearchitecture.chapter03.section_3_3_1_srp.after;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: SRP (AFTER) ===");
        System.out.println("Responsibilities are cleanly delegated to specific classes!\n");

        Player player = new Player("Alex");
        TacticsEngine tactics = new TacticsEngine();
        PlayerRepository repository = new PlayerRepository();
        
        player.dribbleBall();
        tactics.determineBestPosition(player);
        repository.saveStats(player);

        System.out.println("\n===============================\n");
    }
}