const Option = require('./option');
const DecisionMaker = require('./decisionMaker');

/**
 * Demonstrates how to configure and execute the Weighted Decision Model.
 */
class Demo {
    static run() {
        console.log("--- Weighted Decision Model Example ---");

        // STEP 1: Define the architectural options and score them (1 to 5).
        const options = [
            new Option("InMemory", { availability: 1, performance: 5, simplicity: 5 }),
            new Option("Redis",    { availability: 5, performance: 4, simplicity: 3 }),
            new Option("Database", { availability: 4, performance: 2, simplicity: 4 })
        ];

        const decisionMaker = new DecisionMaker();

        // SCENARIO 1: High Availability is the priority.
        console.log("\n[SCENARIO 1: Prioritizing Availability]");
        const availabilityWeights = { availability: 0.6, performance: 0.3, simplicity: 0.1 };
        const result1 = decisionMaker.pickOption(options, availabilityWeights);
        console.log(result1.rationale);

        // SCENARIO 2: Performance and Simplicity are the priorities.
        console.log("\n[SCENARIO 2: Prioritizing Performance & Simplicity]");
        const performanceWeights = { availability: 0.1, performance: 0.5, simplicity: 0.4 };
        const result2 = decisionMaker.pickOption(options, performanceWeights);
        console.log(result2.rationale);

        console.log("---------------------------------------\n");
    }
}

module.exports = Demo;