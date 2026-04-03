const PortfolioManager = require('./portfolioManager');

/**
 * A demonstration of why Tight Coupling ruins testability.
 */
class AttemptedTest {
    static async run() {
        console.log("\n--- ATTEMPTING TO TEST (BEFORE) ---");
        
        const manager = new PortfolioManager();

        console.log("Test Action: Calculating value of 1 BTC...");
        
        try {
            const value = await manager.calculateTotalValue(1.0);
            
            // ASSERT
            // We cannot write a reliable test assertion because the data is live.
            console.log(`Result: ${value}`);
            console.log("FAIL: This test is FLAKY. We cannot assert a fixed price.");
        } catch (error) {
            console.log("CRASH: Test failed completely. No internet connection or API is down.");
        }
    }
}

module.exports = AttemptedTest;