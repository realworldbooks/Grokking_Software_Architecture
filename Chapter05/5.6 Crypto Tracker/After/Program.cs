using System;
using System.Threading.Tasks;
using CryptoTracker.After.Core.Domain;
using CryptoTracker.After.Core.Ports;
using CryptoTracker.After.Infrastructure.Adapters;
using CryptoTracker.After.Tests;

namespace CryptoTracker.After
{
    class Program
    {
        static async Task Main(string[] args)
        {
            Console.WriteLine("--- CRYPTO TRACKER HEXAGONAL ---");

            // 1. Choose your Adapter (The Plug)
            // IPriceProviderPort adapter = new CoinGeckoAdapter(); // Use this for real data
            IPriceProviderPort adapter = new FakePriceProvider();   // Use this for "Airplane Mode"

            // 2. Inject it into the Core (The Socket)
            var manager = new PortfolioManager(adapter);

            // 3. Run the logic
            var value = await manager.CalculateTotalValue(2.5m);
            Console.WriteLine($"Portfolio Value (BTC @ ${await adapter.GetBitcoinPrice()}): ${value}");

            // 4. Run the Proof (The Test)
            await PortfolioTests.Run();
        }
    }
}