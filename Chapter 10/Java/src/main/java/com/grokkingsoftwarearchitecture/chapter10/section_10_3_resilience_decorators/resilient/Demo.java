package com.grokking.resilience;

import com.grokking.resilience.core.application.CheckoutOrchestrator;
import com.grokking.resilience.infrastructure.adapters.ZebraPaymentAdapter;
import com.grokking.resilience.infrastructure.adapters.MockMessageQueueAdapter;

public class Demo {
    public static void runResilienceScenario() {
        System.out.println("\n=== Chapter 10.3: Resilience in Java (Hexagonal + Resilience4j) ===");

        // 1. ASSEMBLY (Dependency Injection)
        // We fetch the configuration from the environment, not hardcoded strings.
        String apiUrl = System.getenv().getOrDefault("PAYMENT_API_URL", "https://api.zebra.com");
        
        var paymentAdapter = new ZebraPaymentAdapter(apiUrl);
        var queueAdapter = new MockMessageQueueAdapter();
        
        var orchestrator = new CheckoutOrchestrator(paymentAdapter, queueAdapter);

        // 2. SCENARIO EXECUTION
        System.out.println("--- SCENARIO: Network unstable, executing Resilience4j-shielded adapter ---");
        var result = orchestrator.processCheckout("ORD-JVM-101", 450.00);
        
        System.out.println("      [Final Result] Orchestrator returned status: " + result);

        // 3. THE ARCHITECTURAL VERDICT
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ARCHITECTURAL VERDICT:");
        System.out.println("-".repeat(60));
        System.out.println("RESILIENCE4J: Policy is physically locked in the Adapter (Core stays pure).");
        System.out.println("GRACEFUL DEGRADATION: Fallback to MessageQueue is a first-class Port.");
        System.out.println("IDEMPOTENCY: Safely generated in Application Layer to survive retries.");
        System.out.println("\nREALITY CHECK: A Clarity Engineer builds bulkheads, so a leak");
        System.out.println("in one port (Payment) doesn't sink the whole ship.");
        System.out.println("=".repeat(60));
    }
}