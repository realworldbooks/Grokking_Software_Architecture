import { lastValueFrom } from 'rxjs';
import { CheckoutOrchestrator } from './core/application/CheckoutOrchestrator.js';
import { ZebraPaymentAdapter } from './infrastructure/adapters/ZebraPaymentAdapter.js';
import { LocalMessageQueueAdapter } from './infrastructure/adapters/LocalMessageQueueAdapter.js';

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
export class Demo {
    static async run() {
        console.log("\n=== Chapter 10.3: Resilience with Local Persistence (RxJS) ===");

        // ASSEMBLY: Wiring concrete tools into abstract ports
        const paymentAdapter = new ZebraPaymentAdapter();
        const queueAdapter = new LocalMessageQueueAdapter();
        
        // The Orchestrator (Core) is instantiated with its dependencies injected.
        const orchestrator = new CheckoutOrchestrator(paymentAdapter, queueAdapter);

        console.log("--- SCENARIO: Simulating Primary Failure -> RxJS Fallback ---");
        
        // lastValueFrom converts the stream to a Promise at the boundary for menu.py
        const result = await lastValueFrom(
            orchestrator.processCheckout("ORD-RX-77", 199.99)
        );
        
        console.log(`      [Final Result] Transaction State: ${result}`);

        const separator = "=".repeat(60);
        const subSeparator = "-".repeat(60);
        
        console.log(`\n${separator}`);
        console.log("ARCHITECTURAL VERDICT: THE RESILIENT WAY WITH MESSAGE QUEUE FALLBACK");
        console.log(subSeparator);
        console.log("DURABILITY: Failure data is secured to disk (SQLite), not lost in RAM.");
        console.log("ZERO-TRUST: No external accounts or servers needed for the lab.");
        console.log("PURITY: The business logic is 100% library-agnostic.");
        console.log("\nREALITY CHECK: A Clarity Engineer ensures the Core survives.");
        console.log(`${separator}\n`);
    }
}