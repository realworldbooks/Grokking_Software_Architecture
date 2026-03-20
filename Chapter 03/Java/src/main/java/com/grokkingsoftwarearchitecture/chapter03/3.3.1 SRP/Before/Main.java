package com.grokkingsoftwarearchitecture.chapter03.srp.before;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: SRP (BEFORE) ===");
        System.out.println("The Player class is doing way too much work!\n");

        Player player = new Player("Alex");
        
        player.dribbleBall();
        player.determineBestPosition();
        player.saveStatsToDatabase();

        System.out.println("\n===============================\n");
    }
}