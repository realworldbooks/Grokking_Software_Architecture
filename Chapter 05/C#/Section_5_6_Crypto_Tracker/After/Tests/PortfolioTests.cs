using System;
using System.Threading.Tasks;
using Chapter05.CryptoTracker.After.Core.Domain;
using Chapter05.CryptoTracker.After.Infrastructure.Adapters;

namespace Chapter05.CryptoTracker.After.Tests
{
    /// <summary>
    /// ARCHITECTURAL TEST
    /// Fulfills the Scribe role by proving the test passes with 100% reliability.
    /// </summary>
    public static class PortfolioTests
    {
        public static async Task Run()
        {
            Console.WriteLine("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
            
            // Arrange
            // We use the Fake adapter so we know EXACTLY what the price is ($50,000)
            var fakeAdapter = new FakePriceProvider(50_000m);
            var manager = new PortfolioManager(fakeAdapter);

            // Act
            Console.WriteLine("Test Action: Calculating value of 2 BTC at fixed $50,000 price...");
            var value = await manager.CalculateTotalValue(2m);

            // Assert
            if (value == 100_000m) 
            {
                Console.WriteLine("SUCCESS: The portfolio correctly calculated $100,000. Test is 100% stable!");
            }
            else 
            {
                Console.WriteLine("FAIL: Math error in Core logic.");
            }
        }
    }
}