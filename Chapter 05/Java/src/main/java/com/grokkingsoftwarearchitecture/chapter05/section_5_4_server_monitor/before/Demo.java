package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.before;

import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * The Execution Layer.
 * Acts as the "Chief Explainer" for the user menu.
 */
public class Demo {
    
    private Demo() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Entry point for the "Before" architectural scenario.
     */
    public static void run() {
        LogManager.info(Demo.class, "--- SERVER MONITOR (BEFORE) ---");
        runScenario();
        LogManager.info(Demo.class, "\n--- SCENARIO COMPLETE ---");
        LogManager.info(Demo.class, "\n========================================");
    }

    /**
     * Demonstrates the nominal and failure cases in the "Happy Path".
     */
    public static void runScenario() {
        ServerMonitor monitor = new ServerMonitor();
        
        LogManager.info(Demo.class, "Check 80 degrees: ");
        monitor.checkTemperature(80); 
        
        LogManager.info(Demo.class, "Check 96 degrees: ");
        monitor.checkTemperature(96);

        LogManager.info(Demo.class, "\n----------------------------------------\n");

        // Fulfilling the Scribe role by documenting the test failure.
        AttemptedTest.run();
    }
}