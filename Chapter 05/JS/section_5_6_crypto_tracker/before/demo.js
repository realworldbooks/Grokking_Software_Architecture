const PortfolioManager = require('./portfolioManager');
const AttemptedTest = require('./attemptedTest');

/**
 * The Execution Layer.
 */
class Demo {
    static async run() {
        console.log("--- STARTING SCENARIO: CRYPTO TRACKER (BEFORE) ---");
        
        const manager = new PortfolioManager();
        
        try {
            process.stdout.write("Calculating live value of 2 BTC... ");
            const value = await manager.calculateTotalValue(2.0);
            console.log(`Portfolio Value: $${value}`);
        } catch(error) {
            console.log(`\nFailed. Do you have internet? ${error.message}`);
        }

        console.log("\n----------------------------------------");

        await AttemptedTest.run();
        
        console.log("\n========================================");
    }
}

if (require.main === module) {
    Demo.run();
}

module.exports = Demo;