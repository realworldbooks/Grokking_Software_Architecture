using System;

namespace CryptoTracker.Before
{
    class Program
    {
        static void Main(string[] args)
        {
            var manager = new PortfolioManager();
            
            try 
            {
                // This will fail if you don't have internet!
                var value = manager.CalculateTotalValue(2.5m);
                Console.WriteLine($"Portfolio Value: ${value}");
            }
            catch(Exception ex) 
            {
                Console.WriteLine("Failed. Do you have internet? Did the API change? " + ex.Message);
            }

            //Attempt to Test
            AttemptedTest.Run()
        }
    }
}