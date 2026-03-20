const Coach = require('./coach');
const Forward = require('./forward');
const Midfielder = require('./midfielder');
const Winger = require('./winger');

console.log("=== Chapter 3: DIP (AFTER) ===");
console.log("The Coach depends on abstractions, allowing for easy team changes!\n");

const team = [
    new Forward(),
    new Midfielder(),
    new Winger()
];

const coach = new Coach(team);
coach.executeGamePlan();

console.log("\n===============================\n");