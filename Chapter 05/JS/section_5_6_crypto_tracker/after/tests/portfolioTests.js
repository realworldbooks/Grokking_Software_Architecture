const PortfolioManager = require('../core/domain/portfolioManager');
const FakePriceProvider = require('../infrastructure/adapters/fakePriceProvider');

/**
 * ARCHITECTURAL TEST.
 * Fulfills the 'Scribe' role by using a deterministic Fake to verify 
 * business logic without external dependencies.
 */
class PortfolioTests {
    /**
     * Executes the hexagonal test suite for the Crypto Tracker.
     * Uses async/await to handle the asynchronous nature of price retrieval.
     */
    static async run() {
        console.log("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
        
        // Arrange: Inject a Fake adapter with a fixed price to ensure stability
        const fakeAdapter = new FakePriceProvider(50000);
        const manager = new PortfolioManager(fakeAdapter);

        // Act: We must await the result because the Domain now returns a Promise
        console.log("Test Action: Calculating value of 2 BTC at fixed $50,000 price...");
        const value = await manager.calculateTotalValue(2.0);

        // Assert: Verify the logic matches our expected fixed input
        if (value === 100000) {
            console.log("SUCCESS: The portfolio correctly calculated $100,000. Test is stable!");
        } else {
            console.log("FAIL: Math error in Core logic.");
        }
    }
}

module.exports = PortfolioTests;