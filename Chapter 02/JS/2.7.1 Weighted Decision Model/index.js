const Option = require('./option');
const { pickOption } = require('./decisionMaker');

console.log("--- Running Weighted Decision Model Example ---");

// 1. Define our options using the Option model and score them from 1 (worst) to 5 (best)
const options = [
    new Option("InMemory", { availability: 1, performance: 5, simplicity: 5 }),
    new Option("Redis",    { availability: 5, performance: 4, simplicity: 3 }),
    new Option("Database", { availability: 4, performance: 2, simplicity: 4 })
];

// 2. Define our priorities: Availability is most important (60%).
console.log("\nScenario 1: Prioritizing Availability");
const ourPriorities = { availability: 0.6, performance: 0.3, simplicity: 0.1 };

// 3. Get the decision!
let decision = pickOption(options, ourPriorities);
console.log(decision.rationale);

// 4. Define new priorities: Performance and Simplicity are most important.
console.log("\nScenario 2: Prioritizing Performance & Simplicity");
const newPriorities = { availability: 0.1, performance: 0.5, simplicity: 0.4 };

decision = pickOption(options, newPriorities);
console.log(decision.rationale);

console.log("-----------------------------------------------");