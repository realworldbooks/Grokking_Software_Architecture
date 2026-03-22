const Option = require('./option');
const { pickOption } = require('./decisionMaker');

function runWeightedDecisionModelDemo() {
    console.log("--- Weighted Decision Model Example ---");

    // STEP 1: Define the architectural options and score them.
    // Score each option on a scale of 1 (bad) to 5 (good) for each criterion.
    const options = [
        new Option("InMemory", { availability: 1, performance: 5, simplicity: 5 }),
        new Option("Redis",    { availability: 5, performance: 4, simplicity: 3 }),
        new Option("Database", { availability: 4, performance: 2, simplicity: 4 })
    ];

    // ---
    // SCENARIO 1: The project's highest priority is high availability.
    // ---
    console.log("\\n[SCENARIO 1: Prioritizing Availability]");
    
    // STEP 2: Define the weights based on current priorities.
    // Weights should sum to 1.0. "availability" gets a weight of 0.6 (60%).
    const availabilityFocusedWeights = { availability: 0.6, performance: 0.3, simplicity: 0.1 };

    // STEP 3: Run the model and get the decision.
    let decision1 = pickOption(options, availabilityFocusedWeights);
    console.log(decision1.rationale);
    // With these weights, Redis is the winner.

    // ---
    // SCENARIO 2: Priorities change. Now, performance and simplicity are key.
    // ---
    console.log("\\n[SCENARIO 2: Prioritizing Performance & Simplicity]");
    
    // STEP 2 (Re-run): Define a new set of weights.
    const performanceFocusedWeights = { availability: 0.1, performance: 0.5, simplicity: 0.4 };

    // STEP 3 (Re-run): Get the new decision.
    let decision2 = pickOption(options, performanceFocusedWeights);
    console.log(decision2.rationale);
    // By changing the weights, the model now recommends the InMemory option.

    console.log("---------------------------------------\\n");
}

runWeightedDecisionModelDemo();