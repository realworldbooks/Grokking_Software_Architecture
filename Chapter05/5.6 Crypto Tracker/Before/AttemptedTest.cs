using System;

namespace CryptoTracker.Before
{
    public class AttemptedTest
    {
        public static void Run()
        {
            Console.WriteLine("\n--- ATTEMPTING TO TEST (BEFORE) ---");
            
            var manager = new PortfolioManager();

            try 
            {
                // ACT
                // We want to test that 1 BTC = the current price.
                decimal value = manager.CalculateTotalValue(1m);

                // ASSERT
                // Problem: What is the price of Bitcoin right now? 
                // Is it $50,000? $60,000? $20,000?
                // We cannot write an assertion because the data changes every second!
                
                if (value == 50_000m) 
                {
                     Console.WriteLine("PASS: Value is exactly 50,000 (One in a million chance!)");
                }
                else 
                {
                     Console.WriteLine($"FAIL: Expected 50,000 but got {value}.");
                     Console.WriteLine("      (Test is flaky because we depend on live data.)");
                }
            }
            catch
            {
                Console.WriteLine("FAIL: Test crashed (No Internet / API Down).");
            }
        }
    }
}
