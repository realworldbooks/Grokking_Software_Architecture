const PortfolioManager = require('./portfolioManager');

/**
 * A demonstration of why Tight Coupling ruins testability.
 */
class AttemptedTest {
    static async run() {
        console.log("\n--- ATTEMPTING TO TEST (BEFORE) ---");
    
    const btcAmount = 2.5; // Using a non-integer to prove it handles decimals
    const monitor = new PortfolioManager();
    const usd = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' });

    console.log(`Test Action: Verifying portfolio for ${btcAmount} BTC...`);
    
    const result = monitor.calculateTotalValue(btcAmount);
    
    // NEW: Showing the math in the test output
    console.log(`----------------------------------------`);
    console.log(`Quantity: ${btcAmount} BTC`);
    console.log(`Market Price: ${usd.format(result / btcAmount)}`);
    console.log(`Total Value: ${usd.format(result)}`);
    console.log(`----------------------------------------`);

    if (result > 0) {
        console.log("SUCCESS: Portfolio calculation verified.");
    } else {
        console.error("FAIL: Calculation returned zero or invalid data.");
    }
}}

module.exports = AttemptedTest;