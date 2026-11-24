const Dashboard = require('./dashboard');

/**
 * Demonstrates the slow performance of a non-cached dashboard.
 */
class Demo {
    static async run() {
        console.log("--- Performance Example: Caching (BEFORE) ---");
        console.log("\n[SCENARIO 1: Before Refactor - No Caching]");
        console.log("Notice how slow this is. Every request hits the database.\n");
        
        const userId = "user123";
        const dashboard = new Dashboard();
        
        // Using console.time for easy performance measurement in Node.js
        console.time(">> Time taken");
        
        await dashboard.getDashboardSummary(userId);
        
        console.timeEnd(">> Time taken");
        console.log("--------------------------------------------------\n");
    }
}

module.exports = Demo;