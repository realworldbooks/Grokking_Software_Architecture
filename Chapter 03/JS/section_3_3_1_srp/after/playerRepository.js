/**
 * Responsibility 3: Data Persistence.
 * * ARCHITECTURE NOTE: This class handles all infrastructure concerns. If we 
 * move from a local MongoDB to a cloud-based PostgreSQL, or even just a 
 * JSON file for local development, this is the ONLY file that needs to change. 
 * * The rest of the game's logic remains completely unaware of how the data 
 * is actually stored.
 */
class PlayerRepository {
    /**
     * Persists the player's statistics to the underlying data store.
     * @param {Player} player - The player whose stats need saving.
     */
    saveStats(player) {
        console.log(`  [Database] Saving ${player.name}'s game stats to the database.`);
    }
}

module.exports = PlayerRepository;