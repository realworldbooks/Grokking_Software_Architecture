using System;

namespace Chapter05.CryptoTracker.Before
{
    /// <summary>
    /// A demonstration of why Tight Coupling ruins testability.
    /// </summary>
    public static class AttemptedTest
    {
        public static void Run()
        {
            Console.WriteLine("\n--- ATTEMPTING TO TEST (BEFORE) ---");
            
            // Arrange
            // We create the manager, but it creates its own hardcoded HTTP client inside!
            var manager = new PortfolioManager();

            // Act
            Console.WriteLine("Test Action: Calculating value of 1 BTC...");
            
            try 
            {
                var value = manager.CalculateTotalValue(1m);
                
                // ASSERT
                // Problem: What is the price of Bitcoin right now? 
                // We cannot write a reliable assertion because the data changes every second!
                Console.WriteLine($"Result: {value}.");
                Console.WriteLine("FAIL: This test is FLAKY. We can't use Assert.AreEqual(50000) " +
                                  "because the live price is always changing.");
            }
            catch (Exception)
            {
                Console.WriteLine("CRASH: The test failed completely because the live API " +
                                  "is unreachable or the network is down.");
            }
        }
    }
}