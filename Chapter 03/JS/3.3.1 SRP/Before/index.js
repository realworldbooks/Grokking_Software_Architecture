const Player = require('./player');

console.log("=== Chapter 3: SRP (BEFORE) ===");
console.log("The Player class is doing way too much work!\n");

const player = new Player("Alex");

player.dribbleBall();
player.determineBestPosition();
player.saveStatsToDatabase();

console.log("\n===============================\n");