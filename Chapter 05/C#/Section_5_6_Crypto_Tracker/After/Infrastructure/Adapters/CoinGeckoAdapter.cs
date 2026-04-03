using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using Chapter05.CryptoTracker.After.Core.Ports;

namespace Chapter05.CryptoTracker.After.Infrastructure.Adapters
{
    /// <summary>
    /// ADAPTER 2: The Real Production Adapter.
    /// Encapsulates all the messy HTTP calls and 3rd-party JSON shapes here, 
    /// far away from our pure business logic.
    /// </summary>
    public class CoinGeckoAdapter : IPriceProviderPort
    {
        public async Task<decimal> GetBitcoinPrice()
        {
            using var client = new HttpClient();
            client.DefaultRequestHeaders.Add("User-Agent", "C# App");
            
            var json = await client.GetStringAsync("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd");
            
            // If CoinGecko changes their JSON structure, ONLY this file breaks.
            var priceData = JsonSerializer.Deserialize<Dictionary<string, Dictionary<string, decimal>>>(json);

            // NULL CHECK
            if (priceData == null || !priceData.ContainsKey("bitcoin"))
            {
                throw new Exception("Failed to parse pricing data from the CoinGecko API.");
            }
            return priceData["bitcoin"]["usd"];
        }
    }
}