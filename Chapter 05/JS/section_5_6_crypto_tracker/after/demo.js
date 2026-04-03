const PortfolioManager = require('./core/domain/portfolioManager');
const CoinGeckoAdapter = require('./infrastructure/adapters/coinGeckoAdapter');
const PortfolioTests = require('./tests/portfolioTests');

/**
 * The Execution Layer.
 */
class Demo {
    static async run() {
        console.log("--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---");

        const realAdapter = new CoinGeckoAdapter(); 
        const manager = new PortfolioManager(realAdapter);

        try {
            const value = await manager.calculateTotalValue(2.0);
            console.log(`Live Portfolio Value: $${value}`);
        } catch (error) {
            console.log(`Live API failed, but architecture is safe: ${error.message}`);
        }

        console.log("\n----------------------------------------\n");

        await PortfolioTests.run();
        
        console.log("\n========================================");
    }
}

if (require.main === module) {
    Demo.run();
}

module.exports = Demo;