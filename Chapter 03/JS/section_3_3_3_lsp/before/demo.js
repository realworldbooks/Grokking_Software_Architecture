const Coach = require('./coach');
const Goalie = require('./goalie');

class Demo {
    static run() {
        console.log("=== Chapter 3: LSP (BEFORE) ===");
        console.log("Passing a Goalie as a generic Player breaks the contract!\n");

        const coach = new Coach();
        const goalie = new Goalie();

        coach.directFieldPlay(goalie);

        console.log("\n===============================\n");
    }
}

module.exports = Demo;