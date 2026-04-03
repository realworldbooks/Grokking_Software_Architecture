using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;

namespace Chapter05.CryptoTracker.Before
{
    /// <summary>
    /// THE CORE (Tightly Coupled).
    /// WARNING: This class is a major architectural liability. 
    /// It mixes pure domain logic (calculating portfolio value) with 
    /// infrastructure concerns (making HTTP calls to external APIs).
    /// </summary>
    public class PortfolioManager
    {
        /// <summary>
        /// Calculates the total USD value of a Bitcoin balance.
        /// </summary>
        public decimal CalculateTotalValue(decimal btcAmount)
        {
            // VIOLATION 1: Hard-coded infrastructure dependency.
            // By 'new-ing' up an HttpClient here, we tie our business logic 
            // directly to the network interface.
            using var client = new HttpClient();
            client.DefaultRequestHeaders.Add("User-Agent", "C# App"); 

            // VIOLATION 2: Synchronous execution over the network.
            // If the CoinGecko API goes down, our entire application hangs.
            var json = client.GetStringAsync("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd").Result;
            
            // VIOLATION 3: The logic is tangled with a specific external JSON format.
            // If CoinGecko changes their response structure, our Core breaks.
            var priceData = JsonSerializer.Deserialize<Dictionary<string, Dictionary<string, decimal>>>(json);
            
            //NULL CHECK
            if (priceData == null || !priceData.ContainsKey("bitcoin"))
            {
                throw new Exception("Failed to parse pricing data from the API.");
            }
            var currentPrice = priceData["bitcoin"]["usd"];
            
            return btcAmount * currentPrice;
        }
    }
}