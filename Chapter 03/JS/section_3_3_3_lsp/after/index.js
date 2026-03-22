const Coach = require('./coach');
const Midfielder = require('./midfielder');
const Forward = require('./forward');

console.log("=== Chapter 3: LSP (AFTER) ===");
console.log("Subclasses perfectly fulfill the parent contract!\n");

const coach = new Coach();
const midfielder = new Midfielder();
const forward = new Forward();

coach.directFieldPlay(midfielder);
console.log();
coach.directFieldPlay(forward);

console.log("\n===============================\n");