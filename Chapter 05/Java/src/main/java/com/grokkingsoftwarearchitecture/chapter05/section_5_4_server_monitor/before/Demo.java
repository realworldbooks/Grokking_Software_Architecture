package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.before;

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
        System.out.println("--- SERVER MONITOR (BEFORE) ---");
        runScenario();
        System.out.println("\n--- SCENARIO COMPLETE ---");
        System.out.println("\n========================================");
    }

    /**
     * Demonstrates the nominal and failure cases in the "Happy Path".
     */
    public static void runScenario() {
        ServerMonitor monitor = new ServerMonitor();
        
        System.out.println("Check 80 degrees: ");
        monitor.checkTemperature(80); 
        
        System.out.println("Check 96 degrees: ");
        monitor.checkTemperature(96);

        System.out.println("\n----------------------------------------\n");

        // Fulfilling the Scribe role by documenting the test failure.
        AttemptedTest.run();
    }
}