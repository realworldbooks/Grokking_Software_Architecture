using System;
using System.Threading.Tasks;
using CryptoTracker.After.Core.Domain;
using CryptoTracker.After.Infrastructure.Adapters;

namespace CryptoTracker.After.Tests
{
    public class PortfolioTests
    {
        public static async Task Run()
        {
            Console.WriteLine("\n--- RUNNING PORTFOLIO UNIT TEST ---");

            // Arrange
            // We use the Fake adapter so we know EXACTLY what the price is ($50,000)
            var offlineAdapter = new FakePriceProvider();
            var manager = new PortfolioManager(offlineAdapter);

            // Act
            // We calculate value for 2.5 BTC
            var value = await manager.CalculateTotalValue(2.5m);

            // Assert
            // 2.5 * 50,000 should be 125,000
            if (value == 125_000m)
            {
                Console.WriteLine("PASS: Calculated correct value offline!");
            }
            else
            {
                Console.WriteLine($"FAIL: Expected 125,000 but got {value}");
            }
        }
    }
}