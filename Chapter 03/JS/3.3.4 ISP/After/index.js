const Midfielder = require('./midfielder');
const Goalie = require('./goalie');

console.log("=== Chapter 3: ISP (AFTER) ===");
console.log("Interfaces are segregated. No more Exceptions!\n");

const midfielder = new Midfielder();
midfielder.practiceShooting();

console.log();

const goalie = new Goalie();
goalie.practiceDivingSaves();
goalie.practiceHandDistribution();

console.log("\n===============================\n");