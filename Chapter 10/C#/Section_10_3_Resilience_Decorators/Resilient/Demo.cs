using System;
using System.Threading.Tasks;
using Chapter10.Resilience.Core.Application;
using Chapter10.Resilience.Infrastructure.Adapters;

namespace Chapter10.Resilience;

public class Demo
{
    public static async Task RunResilienceScenario()
    {
        Console.WriteLine("\n=== Chapter 10.3: Resilience in C# (Hexagonal + Polly) ===");

        // Assembly (Dependency Injection)
        // #I: ENVIRONMENT DECOUPLING
        string apiUrl = Environment.GetEnvironmentVariable("PAYMENT_API_URL") ?? "https://api.zebra.com";
        
        var paymentAdapter = new ZebraPaymentAdapter(apiUrl);
        var queueAdapter = new MockMessageQueueAdapter();
        
        var orchestrator = new CheckoutOrchestrator(paymentAdapter, queueAdapter);

        Console.WriteLine("--- SCENARIO: Unstable network, executing Polly-shielded adapter ---");
        var status = await orchestrator.ProcessCheckoutAsync("ORD-NET-101", 250.00m);
        Console.WriteLine($"      [Final Result] Order Status: {status}");

        // THE ARCHITECTURAL VERDICT
        Console.WriteLine("\n" + new string('=', 60));
        Console.WriteLine("ARCHITECTURAL VERDICT:");
        Console.WriteLine(new string('-', 60));
        Console.WriteLine("POLLY: Encapsulates failure policy outside business logic.");
        Console.WriteLine("INTERFACES: Core depends on IPaymentGateway, not ZebraAdapter.");
        Console.WriteLine("IDEMPOTENCY: Safely managed by the Orchestrator.");
        Console.WriteLine("\nREALITY CHECK: Resilience is the art of separating the");
        Console.WriteLine("'What' (Business) from the 'How' (Infrastructure failure).");
        Console.WriteLine(new string('=', 60));
    }
}