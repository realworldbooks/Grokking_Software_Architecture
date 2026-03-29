const ReportGenerator = require('./reportGenerator');

/**
 * Demonstrates the difficulty of testing tightly coupled code.
 */
class Demo {
    static run() {
        console.log("--- Testability Example: Dependency Injection (BEFORE) ---");
        console.log("\n[SCENARIO 1: Before Refactor - Tightly Coupled]");
        console.log("Attempting to unit test the 'ReportGenerator' class...");
        
        // We instantiate the class. Notice its constructor immediately creates
        // a `RealDatabaseConnection`. We have no way to stop this.
        const generator = new ReportGenerator();
        const result = generator.generate("Sales Report");

        // The `RealDatabaseConnection` returns 2 rows.
        // Our test expects 3 rows.
        // This test will therefore fail. More importantly, we are forced to run
        // the test against the `RealDatabaseConnection`, making this an
        // integration test, not a true unit test. It's slow and depends on an
        // external system (the "database").
        const expected = "Report 'Sales Report' generated with 3 rows.";
        console.log("  > Verifying the generated report...");
        
        if (result !== expected) {
            console.log("  ❌ TEST FAILED!");
            console.log(`     Expected: "${expected}"`);
            console.log(`     Received: "${result}"`);
            console.log("     (This fails because the hardcoded RealDatabaseConnection returns 2 rows, but our test expected 3.)");
        }
        console.log("--------------------------------------------------\n");
    }
}

module.exports = Demo;