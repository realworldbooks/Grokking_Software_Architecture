const ReportGenerator = require('./reportGenerator');
const FakeDatabaseConnection = require('./fakeDatabaseConnection');

/**
 * Demonstrates the power of Dependency Injection in testing.
 */
class Demo {
    static run() {
        console.log("--- Testability Example: Dependency Injection (AFTER) ---");
        console.log("\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]");
        console.log("Unit testing the 'ReportGenerator' class with a mock database...");

        // Here is the magic: We create the Fake connection...
        const fakeDb = new FakeDatabaseConnection();
        
        // ...and "inject" it into the generator. 
        // The generator only cares that the object has a 'getData' method.
        const generator = new ReportGenerator(fakeDb);
        
        const result = generator.generate("Sales Report");
        
        // Our fake database returns 3 rows, so our test assertion will now pass.
        const expected = "Report 'Sales Report' generated with 3 rows.";
        console.log("  > Verifying the generated report...");
        
        if (result === expected) {
            console.log(`  ✅ TEST PASSED! Received expected result: "${result}"`);
        } else {
            console.log(`  ❌ TEST FAILED! Received: "${result}"`);
        }
        console.log("--------------------------------------------------\n");
    }
}

module.exports = Demo;