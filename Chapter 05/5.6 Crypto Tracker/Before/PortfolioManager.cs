using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;

namespace CryptoTracker.Before
{
    public class PortfolioManager
    {
        public decimal CalculateTotalValue(decimal btcAmount)
        {
            // VIOLATION: Hard-coded dependency on a specific external API (CoinGecko)
            using var client = new HttpClient();
            client.DefaultRequestHeaders.Add("User-Agent", "C# App"); 

            // Synchronous .Result is bad practice, but typical for "hacked together" code
            var json = client.GetStringAsync("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd").Result;
            
            // Logic is tangled with parsing specific external JSON format
            var priceData = JsonSerializer.Deserialize<Dictionary<string, Dictionary<string, decimal>>>(json);
            var currentPrice = priceData["bitcoin"]["usd"];
            
            return btcAmount * currentPrice;
        }
    }
}
