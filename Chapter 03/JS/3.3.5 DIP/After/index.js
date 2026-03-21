const Coach = require('./coach');
const Forward = require('./forward');
const Midfielder = require('./midfielder');
const Winger = require('./winger');

console.log("=== Chapter 3: DIP (AFTER) ===");
console.log("The Coach depends on abstractions, allowing for easy team changes!\n");

/**
 * ARCHITECTURE NOTE: This is the "Composition Root." This is the only 
 * place where the 'new' keyword should live. We assemble the system 
 * here and inject the dependencies into the high-level modules.
 */
const team = [
    new Forward(),
    new Midfielder(),
    new Winger()
];

const coach = new Coach(team);
coach.executeGamePlan();

console.log("\n===============================\n");