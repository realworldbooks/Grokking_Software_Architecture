package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.tests;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.domain.ServerMonitor;
import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * ARCHITECTURAL TEST
 * Proves that by using Ports, we have restored our Testability.
 */
public class ServerMonitorTests {

    private ServerMonitorTests() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        LogManager.info(ServerMonitorTests.class, "--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
        testServerOverheatingSendsAlertExactlyOnce();
    }

    private static void testServerOverheatingSendsAlertExactlyOnce() {
        // 1. Arrange
        // We use the FakeAlertPort instead of a real API.
        FakeAlertPort fakePort = new FakeAlertPort();
        ServerMonitor monitor = new ServerMonitor(fakePort);

        // 2. Act
        LogManager.info(ServerMonitorTests.class, "Test Action: Checking temperature at 96 degrees...");
        monitor.checkTemperature(96);

        // 3. Assert
        if (fakePort.getSentMessages().size() == 1 && 
            fakePort.getSentMessages().get(0).contains("Take cover")) {
            LogManager.info(ServerMonitorTests.class, "SUCCESS: Alert sent correctly to the Port.");
        } else {
            LogManager.info(ServerMonitorTests.class, "FAIL: Alert logic failed verification.");
        }
    }
}