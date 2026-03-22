package com.grokkingsoftwarearchitecture.chapter03.srp.after;

/**
 * Responsibility 3: Data Persistence.
 * * ARCHITECTURE NOTE: This class handles all database infrastructure. If we decide 
 * to migrate from a SQL database to a NoSQL database, or if our ORM framework 
 * changes, this is the *only* class that needs to be updated. The Player and 
 * Tactics classes remain completely untouched.
 */
public class PlayerRepository {
    
    /**
     * Persists the player's statistics to the underlying data store.
     * * @param player The player whose stats need saving.
     */
    public void saveStats(Player player) {
        System.out.println("  [Database] Saving " + player.name + "'s game stats to the database.");
    }
}