using System;
using System.Threading.Tasks;

namespace Chapter10.Fragile;

/// <summary>
/// THE FRAGILE DEMO COMPOSER:
/// 
/// DESIGN NOTE:
/// This file represents the "Junior Developer" entry point. It demonstrates 
/// the 'Happy Path Fallacy' in action—where we assume the outside world 
/// will always behave.
///
/// ARCHITECTURAL CRITIQUE:
/// 1. TIGHT COUPLING: Notice that this file must instantiate the concrete 
///    'FragilePaymentService' directly. There is no Interface to hide behind. 
///    Changing vendors requires a surgical strike on this file.
/// 
/// 2. CASCADING FAILURE: Because the fragile service lacks retries or 
///    internal timeouts, a single "hiccup" in the FlakyPayments API causes an 
///    unhandled exception that explodes here, potentially killing the 
///    entire application process.
/// 
/// 3. DATA LOSS: There is no 'Plan B'. If the charge fails, the transaction 
///    is simply forgotten. In a professional system, this is a loss of 
///    revenue and customer trust.
/// </summary>
public static class Demo
{
    public static async Task Run()
    {
        Console.WriteLine("\n=== Chapter 10.3: The Fragile Way (C#) ===");

        // We instantiate the liability directly.
        var fragileService = new FragilePaymentService();
        const decimal amountToCharge = 50.00m;

        Console.WriteLine($"--- SCENARIO: Attempting a naked call to FlakyPayments API ---");

        try
        {
            // This is a "Naked Call." No shield, no backoff, no mercy.
            var result = await fragileService.ChargeCreditCardAsync(amountToCharge);

            Console.WriteLine("      [Fragile Result] Success! (Only because the network was stable)");
            Console.WriteLine($"      [Data] {result}");
        }
        catch (Exception ex)
        {
            // In this architecture, 'Resilience' is just a catch block that 
            // prints a failure message while the business loses money.
            Console.WriteLine("      [SYSTEM CRASH] The transaction has failed permanently.");
            Console.WriteLine($"      [Reason] {ex.Message}");
            Console.WriteLine("      [Consequence] The user sees an error screen and the sale is lost.");
        }

        // --- THE ARCHITECTURAL VERDICT ---
        Console.WriteLine("\n" + new string('=', 60));
        Console.WriteLine("ARCHITECTURAL VERDICT: THE LIABILITY");
        Console.WriteLine(new string('-', 60));
        Console.WriteLine("COUPLING: High. The demo is married to the physical network tool.");
        Console.WriteLine("AVAILABILITY: Brittle. Success requires 100% network uptime.");
        Console.WriteLine("SURVIVABILITY: Zero. No retries, no timeouts, no fallback logic.");
        Console.WriteLine("\nREALITY CHECK: This code satisfies the feature request, but it");
        Console.WriteLine("fails as a stable system. It is a debt that will eventually come due.");
        Console.WriteLine(new string('=', 60) + "\n");
    }
}