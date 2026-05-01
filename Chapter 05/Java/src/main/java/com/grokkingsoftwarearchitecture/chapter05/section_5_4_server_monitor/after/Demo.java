package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.domain.ServerMonitor;
import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.adapters.TwilioAdapter;
import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.tests.ServerMonitorTests;

/**
 * The Execution Layer.
 * This is where the Boundary Keeper defines the environment.
 */
public class Demo {

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        System.out.println("--- STARTING SERVER MONITOR (HEXAGONAL) ---");

        // 1. Configuration (Injected from the environment)
        String envApiKey = "SECRET_TWILIO_KEY_12345";
        String envPhoneNumber = "555-999-8888";

        // 2. Adapter Selection (The "Outside")
        TwilioAdapter twilioAdapter = new TwilioAdapter(envApiKey, envPhoneNumber);

        // 3. Dependency Injection into the Core (The "Inside") 
        ServerMonitor monitor = new ServerMonitor(twilioAdapter);

        // 4. Execution
        monitor.checkTemperature(80);  // Nominal case
        monitor.checkTemperature(105); // Failure case triggers the AlertPort

        System.out.println("\n----------------------------------------\n");

        // 5. Automated Verification
        ServerMonitorTests.run();

        System.out.println("\n========================================");
    }
}