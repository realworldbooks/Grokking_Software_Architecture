const Coach = require('./coach');

class Demo {
    static run() {
        console.log("=== Chapter 3: DIP (BEFORE) ===");
        console.log("The Coach is tightly coupled to concrete players.\n");

        const coach = new Coach();
        coach.executeGamePlan();

        console.log("\n===============================\n");
    }
}

module.exports = Demo;