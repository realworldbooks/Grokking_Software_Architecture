using System;
using System.Threading.Tasks;
using Chapter10.Resilient.Core.Application;
using Chapter10.Resilient.Infrastructure.Adapters;

namespace Chapter10.Resilient
{
    /// <summary>
    /// THE DEMO COMPOSER:
    /// * @description
    /// This file serves as the "Composition Root." In a production system, this 
    /// role might be handled by a Dependency Injection framework, but doing it 
    /// manually here makes the Hexagonal boundaries visible.
    ///
    /// ARCHITECTURAL CRITIQUE:
    /// Notice that we import the concrete Adapters here at the edge. We then 
    /// "Plug" them into the Orchestrator. The Orchestrator itself only knows 
    /// about the Ports (Interfaces), which is why it can survive a total 
    /// infrastructure swap with zero code changes to the Core.
    /// </summary>
    public class Demo
    {
        public static async Task Run()
        {
            Console.WriteLine("\n=== Chapter 10.3: Resilience with Local Persistence (C#) ===");

            // 1. ASSEMBLY (Dependency Injection)
            var paymentAdapter = new ZebraPaymentAdapter("https://api.zebra.com");
            var queueAdapter = new LocalQueueAdapter("payment_backlog.db");

            // The Orchestrator (Core) is instantiated with its dependencies injected.
            var orchestrator = new CheckoutOrchestrator(paymentAdapter, queueAdapter);

            // 2. EXECUTION
            Console.WriteLine("--- SCENARIO: Simulating Primary Failure -> Plan B Fallback ---");
            
            var result = await orchestrator.ProcessCheckout("ORD-CS-LOCAL-55", 250.00m);

            Console.WriteLine($"      [Final Result] Transaction State: {result}");

            // --- THE ARCHITECTURAL VERDICT ---
            Console.WriteLine("\n" + new string('=', 60));
            Console.WriteLine("ARCHITECTURAL VERDICT: THE RESILIENT WAY WITH MESSAGE QUEUE FALLBACK");
            Console.WriteLine(new string('-', 60));
            Console.WriteLine("DURABILITY: Failure data is secured to disk (SQLite/LiteDB), not lost in RAM.");
            Console.WriteLine("ZERO-TRUST: No external accounts or servers needed for the lab.");
            Console.WriteLine("PURITY: The business logic is 100% library-agnostic.");
            Console.WriteLine("\nREALITY CHECK: A Clarity Engineer ensures the Core survives.");
            Console.WriteLine(new string('=', 60) + "\n");
        }
    }
}