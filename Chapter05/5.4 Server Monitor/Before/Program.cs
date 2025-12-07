using System;

namespace ServerMonitor.Before
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("--- SERVER MONITOR (BEFORE) ---");
            
            var monitor = new ServerMonitor();
            
            // This will print to console via the fake TwilioClient
            Console.Write("Check 95 degrees: ");
            monitor.CheckTemperature(95); 
            
            // This will print nominal
            Console.Write("Check 80 degrees: ");
            monitor.CheckTemperature(80);

            Console.WriteLine("\n----------------------------------------\n");

            //Attempt to run test
            // This demonstrates why tight coupling makes testing impossible.
            AttemptedTest.Run();

            Console.WriteLine("\n========================================");
        }
    }
}