const Midfielder = require('./midfielder');

console.log("=== Chapter 3: ISP (BEFORE) ===");
console.log("Midfielder is forced to implement Goalie methods!\n");

const player = new Midfielder();
player.practiceShooting();
player.practiceTackling();

try {
    player.practiceDivingSaves(); // This will crash!
} catch (error) {
    console.log(`  [ERROR] ${error.message}`);
}

console.log("\n===============================\n");