const ServerMonitor = require('./serverMonitor');

class AttemptedTest {
    static run() {
        console.log("\n--- ATTEMPTING TO TEST (BEFORE) ---");
        
        const monitor = new ServerMonitor();

        // ACT
        console.log("Test Action: Calling checkTemperature(96)...");
        monitor.checkTemperature(96);

        // ASSERT
        // ... Wait. How do we check if it worked?
        // We can't check 'monitor.sentMessages' because it doesn't exist.
        // We can't mock Twilio because it's 'new'd up' inside the class.
        
        console.log("FAIL: Impossible to verify outcome programmatically.");
        console.log("      (You have to manually check the console logs.)");
    }
}

module.exports = AttemptedTest;