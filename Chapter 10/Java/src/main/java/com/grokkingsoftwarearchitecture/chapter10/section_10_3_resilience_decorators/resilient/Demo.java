package com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient;

import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.application.CheckoutOrchestrator;
import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.infrastructure.adapters.ZebraPaymentAdapter;
import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.infrastructure.adapters.LocalQueueAdapter;

/**
 * THE DEMO COMPOSER:
 * * @description
 * This file serves as the "Composition Root." In a production system, this 
 * role might be handled by a Dependency Injection framework, but doing it 
 * manually here makes the Hexagonal boundaries visible.
 *
 * ARCHITECTURAL CRITIQUE:
 * Notice that we import the concrete Adapters here at the edge. We then 
 * "Plug" them into the Orchestrator. The Orchestrator itself only knows 
 * about the Ports (Interfaces), which is why it can survive a total 
 * infrastructure swap with zero code changes to the Core.
 */
public class Demo {
    /**
     * Entry point for the laboratory execution.
     */
    public static void run() {
        System.out.println("\n=== Chapter 10.3: Resilience with Local Persistence (Java) ===");

        // ASSEMBLY: Wiring concrete infrastructure into abstract core ports
        // We use a local MVStore file for durability without external dependencies.
        var paymentAdapter = new ZebraPaymentAdapter("https://api.zebra.com");
        var queueAdapter = new LocalQueueAdapter("./payment_backlog.db");
        
        // The Orchestrator (Core) is instantiated with its dependencies injected.
        var orchestrator = new CheckoutOrchestrator(paymentAdapter, queueAdapter);

        System.out.println("--- SCENARIO: Simulating Primary Failure -> Plan B Fallback ---");
        
        // Execute the business logic
        var result = orchestrator.processCheckout("ORD-JAVA-LOCAL-99", 499.99);
        
        System.out.println("      [Final Result] Transaction State: " + result);
        
        // --- THE ARCHITECTURAL VERDICT ---
        String separator = "=".repeat(60);
        String subSeparator = "-".repeat(60);

        System.out.println("\n" + separator);
        System.out.println("ARCHITECTURAL VERDICT: THE RESILIENT WAY WITH MESSAGE QUEUE FALLBACK");
        System.out.println(subSeparator);
        System.out.println("DURABILITY: Failure data is secured to disk (SQLite/MVStore), not lost in RAM.");
        System.out.println("ZERO-TRUST: No external accounts or servers needed for the lab.");
        System.out.println("PURITY: The business logic is 100% library-agnostic.");
        System.out.println("\nREALITY CHECK: A Clarity Engineer ensures the Core survives.");
        System.out.println(separator + "\n");
    }
}