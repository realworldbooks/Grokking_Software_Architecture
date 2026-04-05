const FakeGraphQLHandler = require('./fakeGraphQLHandler');

/**
 * The Execution Layer.
 * Demonstrates GraphQL precision by fetching multiple resources in a single call.
 */
class Demo {
    static async run() {
        console.log("--- GRAPHQL PRECISION DEMO ---");
        console.log("Goal: Get chips name AND soda price in 1 call.");

        // 1. WIRE IT UP
        const client = new FakeGraphQLHandler();

        // 2. THE REQUEST (The Shopping List)
        // GraphQL sends the query as a JSON payload in a POST request
        const queryStr = "query { chipItem: product(id: '123') { name } sodaItem: product(id: '456') { price } }";
        const payload = JSON.stringify({ query: queryStr });
        
        const url = "https://api.snackcorp.com/graphql";
        console.log(`\nCalling: POST ${url}`);
        
        const result = await client.post(url, payload);

        // 3. THE VISUAL EVIDENCE
        console.log("\nResult:");
        console.log(result);
        console.log("\nSuccess: Zero over-fetching!");
        console.log("We got exactly what we asked for in ONE call.");
    }
}

if (require.main === module) {
    Demo.run();
}

module.exports = Demo;