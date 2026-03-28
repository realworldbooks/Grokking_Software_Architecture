package com.grokkingsoftwarearchitecture.chapter03.section_3_3_1_srp.after;

/**
 * Responsibility 2: Tactical / AI Logic.
 * * ARCHITECTURE NOTE: Separating tactics into its own engine means we can 
 * easily swap out algorithms (e.g., switching from "Aggressive" to "Defensive" 
 * tactics) without ever touching the Player class. It also makes this complex 
 * logic incredibly easy to unit test in isolation.
 */
public class TacticsEngine {
    
    /**
     * Analyzes the game state to determine the optimal position for a specific player.
     * * @param player The player being analyzed.
     */
    public void determineBestPosition(Player player) {
        System.out.println("  [Tactics] Calculating optimal court position for " + player.name + "...");
    }
}