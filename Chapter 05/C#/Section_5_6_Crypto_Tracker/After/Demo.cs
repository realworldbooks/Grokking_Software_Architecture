using System;
using System.Threading.Tasks;
using Chapter05.CryptoTracker.After.Core.Domain;
using Chapter05.CryptoTracker.After.Core.Ports;
using Chapter05.CryptoTracker.After.Infrastructure.Adapters;
using Chapter05.CryptoTracker.After.Tests;

namespace Chapter05.CryptoTracker.After
{
    /// <summary>
    /// The Execution Layer.
    /// Acts as the 'Chief Explainer' for the user menu.
    /// </summary>
    public static class Demo
    {
        public static async Task Run()
        {
            Console.WriteLine("--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---");

            // 1. Choose your Adapter (The Plug)
            IPriceProviderPort realAdapter = new CoinGeckoAdapter(); 

            // 2. Inject it into the Core (The Socket)
            var manager = new PortfolioManager(realAdapter);

            // 3. Run the Application
            try 
            {
                var value = await manager.CalculateTotalValue(2m);
                Console.WriteLine($"Live Portfolio Value: ${value}");
            }
            catch (Exception ex)
            {
                 Console.WriteLine($"Live API failed (No internet?), but architecture is safe: {ex.Message}");
            }

            Console.WriteLine("\n----------------------------------------\n");

            // 4. Run the Proof (The Test)
            await PortfolioTests.Run();
            
            Console.WriteLine("\n========================================");
        }
    }
}