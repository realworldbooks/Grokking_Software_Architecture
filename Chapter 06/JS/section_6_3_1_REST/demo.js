const FakeRestHandler = require('./fakeRestHandler');

/**
 * The Execution Layer.
 * Demonstrates the REST over-fetching problem in JavaScript.
 */
class Demo {
    static async run() {
        console.log("--- REST OVER-FETCHING DEMO ---");
        console.log("Goal: We only want the price of the chips.");

        // 1. WIRE IT UP
        const client = new FakeRestHandler();

        // 2. MAKE THE CALL
        const url = "https://api.snackcorp.com/products/123";
        console.log(`\nCalling: GET ${url}\n`);
        
        const result = await client.get(url);

        // 3. THE VISUAL EVIDENCE
        console.log("Result:");
        console.log(result);
        console.log("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!");
    }
}

if (require.main === module) {
    Demo.run();
}

module.exports = Demo;