const ServerMonitor = require('../core/domain/serverMonitor');
const FakeAlertPort = require('./fakeAlertPort');

/**
 * ARCHITECTURAL TEST
 * Fulfills the Scribe role by proving the test passes without hitting a real API.
 */
class ServerMonitorTests {
    static run() {
        console.log("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
        
        // Arrange
        const fakePort = new FakeAlertPort();
        const monitor = new ServerMonitor(fakePort);

        // Act
        console.log("Test Action: Checking temperature at 96 degrees...");
        monitor.checkTemperature(96);

        // Assert
        if (fakePort.sentMessages.length === 1 && fakePort.sentMessages[0].includes("Take cover")) {
            console.log("SUCCESS: Alert sent correctly to the Port.");
        } else {
            console.error("FAIL: Alert logic failed verification.");
        }
    }
}

module.exports = ServerMonitorTests;