const ReportGeneratorBefore = require('./reportGeneratorBefore');
const ReportGeneratorAfter = require('./reportGeneratorAfter');

/**
 * This file acts as a test runner to demonstrate the difference in testability
 * between the tightly coupled and loosely coupled report generators.
 */
function runTestabilityDemo() {
    console.log("--- Testability Example: Dependency Injection ---");

    // --- SCENARIO 1: The "Before" Case (Tightly Coupled) ---
    console.log("\n[SCENARIO 1: Before Refactor - Tightly Coupled]");
    console.log("Attempting to unit test the 'ReportGeneratorBefore' class...");

    // We instantiate the class. Its constructor immediately creates a `DatabaseConnection`.
    const generatorBefore = new ReportGeneratorBefore();
    const resultBefore = generatorBefore.generate("Sales Report");

    // The `DatabaseConnection` returns 2 rows. Our test expects 3.
    // The test will fail. More importantly, we are forced to run against the "real"
    // database connection, making this an integration test, not a unit test.
    const expectedBefore = "Report 'Sales Report' generated with 3 rows.";
    console.log("  > Verifying the generated report...");
    if (resultBefore !== expectedBefore) {
        console.log("  ❌ TEST FAILED!");
        console.log(`     Expected: "${expectedBefore}"`);
        console.log(`     Received: "${resultBefore}"`);
        console.log("     (This fails because the hardcoded DatabaseConnection returns 2 rows, but our test expected 3.)");
    }

    // --- SCENARIO 2: The "After" Case (Loosely Coupled) ---
    console.log("\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]");
    console.log("Unit testing the 'ReportGeneratorAfter' class with a mock object...");

    // This is our "Fake" or "Mock" object. It's a simple JavaScript object
    // that has a `getData` method, so it satisfies the "contract" that
    // `ReportGeneratorAfter` expects. This is known as "Duck Typing" - 
    // "If it walks like a duck and it quacks like a duck, then it must be a duck."
    const fakeDb = {
        getData: (query) => {
            console.log(`\n  [FAKE DB] Received query: ${query}. Returning fake data.`);
            return ["fake_row1", "fake_row2", "fake_row3"];
        }
    };
    
    // We "inject" our fake object into the constructor.
    const generatorAfter = new ReportGeneratorAfter(fakeDb);
    const resultAfter = generatorAfter.generate("Sales Report");

    // Our fake object returns 3 rows, so the test passes.
    // This is a true unit test: fast, reliable, and dependency-free.
    const expectedAfter = "Report 'Sales Report' generated with 3 rows.";
    console.log("  > Verifying the generated report...");
    if (resultAfter === expectedAfter) {
        console.log(`  ✅ TEST PASSED! Received expected result: "${resultAfter}"`);
    } else {
        console.log("  ❌ TEST FAILED!");
        console.log(`     Expected: "${expectedAfter}"`);
        console.log(`     Received: "${resultAfter}"`);
    }
    console.log("--------------------------------------------------\n");
}

runTestabilityDemo();