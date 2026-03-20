// Responsibility 3: Manages only data saving
class PlayerRepository {
    saveStats(player) {
        console.log(`  [Database] Saving ${player.name}'s game stats to the database.`);
    }
}

module.exports = PlayerRepository;