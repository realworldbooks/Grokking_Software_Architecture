package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.before;

import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * A demonstration of why Tight Coupling is the enemy of Testability.
 */
public class AttemptedTest {

    private AttemptedTest() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Runs a pseudo-test that highlights the lack of Inversion of Control.
     */
    public static void run() {
        LogManager.info(AttemptedTest.class, "\n--- ATTEMPTING TO TEST (BEFORE) ---");
        
        ServerMonitor monitor = new ServerMonitor();

        // ACT
        LogManager.info(AttemptedTest.class, "Test Action: Calling checkTemperature(96)...");
        monitor.checkTemperature(96); 

        // ASSERT
        // ARCHITECTURAL FAIL: We cannot verify the 'sendSms' call 
        // because it is hidden and hardcoded inside 'ServerMonitor'.
        
        LogManager.info(AttemptedTest.class, "FAIL: Impossible to verify outcome programmatically.");
        LogManager.info(AttemptedTest.class, "      (You have to manually check the console logs.)");
    }
}