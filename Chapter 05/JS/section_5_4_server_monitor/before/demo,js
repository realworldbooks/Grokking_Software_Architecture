const ServerMonitor = require('./serverMonitor');
const AttemptedTest = require('./attemptedTest');

/**
 * The Execution Layer.
 * This class acts as the "Chief Explainer," coordinating the 
 * demonstration of the tightly coupled system.
 */
class Demo {
    static run() {
        console.log("--- STARTING SCENARIO: TIGHT COUPLING (BEFORE) ---");
        
        // Step 1: Show the Happy Path / Real World usage
        // This demonstrates how the core logic is shackled to the infrastructure.
        const monitor = new ServerMonitor();
        
        process.stdout.write("Check 80 degrees: ");
        monitor.checkTemperature(80);
        
        process.stdout.write("Check 96 degrees: ");
        monitor.checkTemperature(96);

        console.log("\n----------------------------------------");

        // Step 2: Demonstrate the testing failure
        AttemptedTest.run();

        console.log("\n--- SCENARIO COMPLETE ---");
    }
}

// If this file is run directly via node demo.js
if (require.main === module) {
    Demo.run();
}

module.exports = Demo;