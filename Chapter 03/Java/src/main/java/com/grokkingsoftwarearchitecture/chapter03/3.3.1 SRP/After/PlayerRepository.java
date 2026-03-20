package com.grokkingsoftwarearchitecture.chapter03.srp.after;

// Responsibility 3: Manages only data saving
public class PlayerRepository {
    public void saveStats(Player player) {
        System.out.println("  [Database] Saving " + player.name + "'s game stats to the database.");
    }
}