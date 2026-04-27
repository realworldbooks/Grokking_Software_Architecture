import { FragilePaymentService } from './fragilePaymentService.js';

/**
 * THE FRAGILE DEMO COMPOSER:
 * * @description
 * This file demonstrates the "Junior Developer" entry point. It represents 
 * the 'Happy Path Fallacy' in action. 
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. ZERO ABSTRACTION: Notice that this file imports the concrete 
 * 'FragilePaymentService' directly. There is no Port/Interface. 
 * If we want to change vendors, we have to rewrite this file.
 * * 2. CASCADING FAILURE: Because the fragile service has no internal 
 * retries or timeouts, a failure in FlakyPayments's API bubbles up and 
 * explodes here. If this were part of a larger chain, the entire 
 * application would crash.
 * * 3. NO SAFETY NET: There is no 'Plan B'. If the charge fails, the 
 * data is simply lost to the ether.
 */

export class Demo {
    
    static async run() {
        console.log("\n=== Chapter 10.3: The Fragile Way (Node.js) ===");
        
        const fragileService = new FragilePaymentService();
        const amountToCharge = 50.00;

        console.log(`--- SCENARIO: Attempting a raw, unprotected call to FlakyPayments ---`);

        try {
            // This is a "Naked Call." It has no shield, no backoff, and no mercy.
            const result = await fragileService.chargeCreditCard(amountToCharge);
            
            console.log("      [Fragile Result] Success! (But only because the network was lucky)");
            console.log(`      [Data] ${JSON.stringify(result)}`);

        } catch (error) {
            // In this architecture, 'Resilience' is just a catch block that 
            // logs a failure and gives up.
            console.error("      [SYSTEM CRASH] The transaction has failed permanently.");
            console.error(`      [Reason] ${error.message}`);
            console.log("        [Consequence] The user sees an error, and the business loses $50.00.");
        }

        // --- THE ARCHITECTURAL VERDICT ---
        console.log("\n" + "=".repeat(60));
        console.log("ARCHITECTURAL VERDICT: THE LIABILITY");
        console.log("-".repeat(60));
        console.log("COUPLING: High. The demo is married to the physical implementation.");
        console.log("AVAILABILITY: Fragile. Success is dependent on 100% network uptime.");
        console.log("SURVIVABILITY: Zero. No retries, no timeouts, no fallbacks.");
        console.log("\nREALITY CHECK: This code works on your laptop, but it dies in the cloud.");
        console.log("=".repeat(60) + "\n");
    }
}  