using System;

namespace ServerMonitor.Before
{
    public class AttemptedTest
    {
        public static void Run()
        {
            Console.WriteLine("\n--- ATTEMPTING TO TEST (BEFORE) ---");
            
            var monitor = new ServerMonitor();

            // ACT
            Console.WriteLine("Test Action: Calling CheckTemperature(95)...");
            monitor.CheckTemperature(95);

            // ASSERT
            // ... Wait. How do we check if it worked?
            // We can't check 'monitor.SentMessages' because it doesn't exist.
            // We can't mock Twilio because it's 'new'd up' inside the class.
            
            Console.WriteLine("FAIL: Impossible to verify outcome programmatically.");
            Console.WriteLine("      (You have to manually check the console logs.)");
        }
    }
}