const Midfielder = require('./midfielder');

class Demo {
    static run() {
        console.log("=== Chapter 3: OCP (BEFORE) ===");
        console.log("Midfielder uses hardcoded if/else logic for plays.\n");

        const midfielder = new Midfielder();
        midfielder.executePlay("DribblePastOpponent");
        midfielder.executePlay("DefensiveFormation");
        midfielder.executePlay("PassToStriker"); // Fails!

        console.log("\n===============================\n");
    }
}

module.exports = Demo;