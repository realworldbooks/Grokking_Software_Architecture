const Dashboard = require('./dashboard');

/**
 * Demonstrates the dramatic performance gains of the Cache-Aside pattern.
 */
class Demo {
    static async run() {
        console.log("--- Performance Example: Caching (AFTER) ---");
        console.log("\n[SCENARIO 2: After Refactor - With Cache-Aside Pattern]");
        
        const userId = "user123";
        const dashboard = new Dashboard();

        // FIRST CALL: Cache Miss
        console.log("\n(First call for a new user... expect a cache miss)");
        console.time(">> Time taken");
        await dashboard.getDashboardSummary(userId);
        console.timeEnd(">> Time taken");

        // SECOND CALL: Cache Hit
        console.log("\n(Second call for the same user... expect a cache hit)");
        console.time(">> Time taken");
        await dashboard.getDashboardSummary(userId);
        console.timeEnd(">> Time taken");
        
        console.log("--------------------------------------------------\n");
    }
}

module.exports = Demo;