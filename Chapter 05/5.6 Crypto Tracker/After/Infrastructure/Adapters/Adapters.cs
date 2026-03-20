using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;

namespace CryptoTracker.After
{
    // ADAPTER 1: The "Airplane Mode" / Test Adapter
    public class FakePriceProvider : IPriceProviderPort
    {
        // Always returns $50,000. No internet needed!
        public Task<decimal> GetBitcoinPrice() => Task.FromResult(50_000m);
    }

    // ADAPTER 2: The Real Adapter
    public class CoinGeckoAdapter : IPriceProviderPort
    {
        public async Task<decimal> GetBitcoinPrice()
        {
            using var client = new HttpClient();
            client.DefaultRequestHeaders.Add("User-Agent", "C# App");
            
            var json = await client.GetStringAsync("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd");
            
            var priceData = JsonSerializer.Deserialize<Dictionary<string, Dictionary<string, decimal>>>(json);
            return priceData["bitcoin"]["usd"];
        }
    }
}