package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.before;

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
        System.out.println("\n--- ATTEMPTING TO TEST (BEFORE) ---");
        
        ServerMonitor monitor = new ServerMonitor();

        // ACT
        System.out.println("Test Action: Calling checkTemperature(96)...");
        monitor.checkTemperature(96); 

        // ASSERT
        // ARCHITECTURAL FAIL: We cannot verify the 'sendSms' call 
        // because it is hidden and hardcoded inside 'ServerMonitor'.
        
        System.out.println("FAIL: Impossible to verify outcome programmatically.");
        System.out.println("      (You have to manually check the console logs.)");
    }
}