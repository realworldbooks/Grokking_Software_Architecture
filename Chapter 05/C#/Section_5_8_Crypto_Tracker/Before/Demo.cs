using System;

namespace Chapter05.CryptoTracker.Before
{
    /// <summary>
    /// The Execution Layer.
    /// Acts as the 'Chief Explainer' for the user menu.
    /// </summary>
    public static class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- STARTING SCENARIO: CRYPTO TRACKER (BEFORE) ---");
            
            var manager = new PortfolioManager();
            
            try 
            {
                // Step 1: The Happy Path. 
                // This will instantly crash if the user doesn't have Wi-Fi!
                Console.Write("Calculating live value of 2 BTC... ");
                var value = manager.CalculateTotalValue(2m);
                Console.WriteLine($"Portfolio Value: ${value}");
            }
            catch(Exception ex) 
            {
                Console.WriteLine("\nFailed. Do you have internet? Did the API change? " + ex.Message);
            }

            Console.WriteLine("\n----------------------------------------");

            // Step 2: Attempt to Test
            AttemptedTest.Run();
            
            Console.WriteLine("\n========================================");
        }
    }
}