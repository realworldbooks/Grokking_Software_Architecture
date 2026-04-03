const PortfolioManager = require('../core/domain/portfolioManager');
const FakePriceProvider = require('../infrastructure/adapters/fakePriceProvider');

/**
 * ARCHITECTURAL TEST
 */
class PortfolioTests {
    static async run() {
        console.log("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
        
        // Arrange
        const fakeAdapter = new FakePriceProvider(50000);
        const manager = new PortfolioManager(fakeAdapter);

        // Act
        console.log("Test Action: Calculating value of 2 BTC at fixed $50,000 price...");
        const value = await manager.calculateTotalValue(2.0);

        // Assert
        if (value === 100000) {
            console.log("SUCCESS: The portfolio correctly calculated $100,000. Test is stable!");
        } else {
            console.log("FAIL: Math error in Core logic.");
        }
    }
}

module.exports = PortfolioTests;