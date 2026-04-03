const ServerMonitor = require('./core/domain/serverMonitor');
const TwilioAdapter = require('./infrastructure/adapters/twilioAdapter');
const ServerMonitorTests = require('./Tests/serverMonitor.test');

/**
 * The Execution Layer.
 * This is where the Boundary Keeper defines the environment.
 * It acts as the 'Chief Explainer' for the Hexagonal architecture.
 */
class Demo {
    static run() {
        console.log("--- STARTING SERVER MONITOR (HEXAGONAL JS) ---");

        // 1. Configuration (Injected from the environment)
        const envApiKey = "SECRET_TWILIO_KEY_12345";
        const envPhoneNumber = "555-999-8888";

        // 2. Adapter Selection (The "Outside")
        const twilioAdapter = new TwilioAdapter(envApiKey, envPhoneNumber);

        // 3. Dependency Injection into the Core (The "Inside")
        const monitor = new ServerMonitor(twilioAdapter);

        // 4. Execution
        process.stdout.write("Check 80 degrees: ");
        monitor.checkTemperature(80);  // Nominal case

        process.stdout.write("Check 105 degrees: ");
        monitor.checkTemperature(105); // Failure case triggers the adapter

        console.log("\n----------------------------------------\n");

        // 5. Automated Verification
        ServerMonitorTests.run();

        console.log("\n========================================");
    }
}

// If this file is run directly via node demo.js
if (require.main === module) {
    Demo.run();
}

module.exports = Demo;