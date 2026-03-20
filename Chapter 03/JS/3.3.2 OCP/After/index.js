const Midfielder = require('./midfielder');
const DribblePastOpponent = require('./dribblePastOpponent');
const DefensiveFormation = require('./defensiveFormation');
const PassToStriker = require('./passToStriker');

console.log("=== Chapter 3: OCP (AFTER) ===");
console.log("Midfielder accepts any class with an execute() method!\n");

const midfielder = new Midfielder();

midfielder.executePlay(new DribblePastOpponent());
midfielder.executePlay(new DefensiveFormation());
midfielder.executePlay(new PassToStriker()); // Success!

console.log("\n===============================\n");