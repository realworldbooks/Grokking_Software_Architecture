const Player = require('./player');
const TacticsEngine = require('./tacticsEngine');
const PlayerRepository = require('./playerRepository');

console.log("=== Chapter 3: SRP (AFTER) ===");
console.log("Responsibilities are cleanly delegated to specific classes!\n");

const player = new Player("Alex");
const tactics = new TacticsEngine();
const repository = new PlayerRepository();

player.dribbleBall();
tactics.determineBestPosition(player);
repository.saveStats(player);

console.log("\n===============================\n");