const ReportGeneratorBefore = require('./reportGeneratorBefore');
const ReportGeneratorAfter = require('./reportGeneratorAfter');

function runTests() {
    console.log("=== Chapter 2: Testability Example ===");

    console.log("\n--- Running Before: Tightly Coupled Test ---");
    try {
        const generatorBefore = new ReportGeneratorBefore();
        const result = generatorBefore.generate("FailingTest");
        const expected = "Report 'FailingTest' generated with 3 rows.";
        
        if (result !== expected) {
            throw new Error(`\n  Expected: "${expected}"\n  Received: "${result}"`);
        }
    } catch (error) {
        console.log("❌ TEST FAILED!" + error.message);
    }

    console.log("\n--- Running After: Loosely Coupled Test ---");
    try {
        const fakeDb = {
            getData: (query) => ["row1", "row2", "row3"]
        };
        
        const generatorAfter = new ReportGeneratorAfter(fakeDb);
        const result = generatorAfter.generate("PassingTest");
        const expected = "Report 'PassingTest' generated with 3 rows.";
        
        if (result === expected) {
            console.log(`✅ TEST PASSED! Received expected result: "${result}"`);
        }
    } catch (error) {
        console.log("❌ TEST FAILED!" + error.message);
    }
    console.log("\n======================================");
}

runTests();