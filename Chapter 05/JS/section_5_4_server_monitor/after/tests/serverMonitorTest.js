const ServerMonitor = require('../core/domain/serverMonitor');
const FakeAlertPort = require('./fakeAlertPort');

/**
 * ARCHITECTURAL TEST.
 * Proves that by using Ports, we can verify business logic in total isolation.
 */
class ServerMonitorTests {
    /**
     * Executes the hexagonal test suite.
     */
    static run() {
        console.log("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
        
        // 1. Arrange: Setup the "Fake" environment
        const fakePort = new FakeAlertPort();
        const monitor = new ServerMonitor(fakePort);

        // 2. Act: Trigger the logic
        console.log("Test Action: Checking temperature at 96 degrees...");
        monitor.checkTemperature(96);

        // 3. Assert: Verify the Scribe recorded the correct interaction
        if (fakePort.sentMessages.length === 1 && fakePort.sentMessages[0].includes("Take cover")) {
            console.log("SUCCESS: Alert sent correctly to the Port.");
        } else {
            console.error("FAIL: Alert logic failed verification.");
        }
    }
}

module.exports = ServerMonitorTests;