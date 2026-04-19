package com.grokking.chapter10.resilience;

import com.grokking.chapter10.resilience.infrastructure.FragilePaymentService;

/**
 * THE FRAGILE DEMO COMPOSER:
 * * DESIGN NOTE:
 * This file represents the "Junior Developer" entry point. It demonstrates 
 * the 'Happy Path Fallacy' in action—where we assume the outside world 
 * will always behave.
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. TIGHT COUPLING: Notice that this file must instantiate the concrete 
 * 'FragilePaymentService' directly. There is no Port/Interface to hide behind. 
 * Changing vendors requires a surgical strike on this file.
 * * 2. CASCADING FAILURE: Because the fragile service lacks retries or 
 * internal timeouts, a single "hiccup" in the Zebra API causes an 
 * unhandled exception that explodes here, potentially killing the 
 * entire JVM thread pool.
 * * 3. DATA LOSS: There is no 'Plan B'. If the charge fails, the transaction 
 * is simply forgotten. In a professional system, this is a loss of 
 * revenue and customer trust.
 */
public class DemoFragile {

    public static void runFragileScenario() {
        System.out.println("\n=== Chapter 10.3: The Fragile Way (Java) ===");

        // We instantiate the liability directly.
        FragilePaymentService fragileService = new FragilePaymentService();
        double amountToCharge = 50.00;

        System.out.println("--- SCENARIO: Attempting a naked call to Zebra API ---");

        try {
            // This is a "Naked Call." No shield, no backoff, no mercy.
            String result = fragileService.chargeCreditCard(amountToCharge);

            System.out.println("      [Fragile Result] Success! (Only because the network was stable)");
            System.out.println("      [Data] " + result);
            
        } catch (Exception ex) {
            // In this architecture, 'Resilience' is just a catch block that 
            // prints a failure message while the business loses money.
            System.out.println("      [SYSTEM CRASH] The transaction has failed permanently.");
            System.out.println("      [Reason] " + ex.getMessage());
            System.out.println("      [Consequence] The user sees an error screen and the sale is lost.");
        }

        // --- THE ARCHITECTURAL VERDICT ---
        System.out.println("\n============================================================");
        System.out.println("ARCHITECTURAL VERDICT: THE LIABILITY");
        System.out.println("------------------------------------------------------------");
        System.out.println("COUPLING: High. The demo is married to the physical network tool.");
        System.out.println("AVAILABILITY: Brittle. Success requires 100% network uptime.");
        System.out.println("SURVIVABILITY: Zero. No retries, no timeouts, no fallback logic.");
        System.out.println("\nREALITY CHECK: This code satisfies the feature request, but it");
        System.out.println("fails as a stable system. It is a debt that will eventually come due.");
        System.out.println("============================================================\n");
    }
}