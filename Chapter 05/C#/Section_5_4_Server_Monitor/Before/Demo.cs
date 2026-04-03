using System;

namespace Chapter05.ServerMonitor.Before
{
    /// <summary>
    /// The Execution Layer.
    /// This class acts as the "Chief Explainer," coordinating the 
    /// demonstration of the tightly coupled system.
    /// </summary>
    public static class Demo
    {
        /// <summary>
        /// Entry point for the "Before" architectural scenario.
        /// </summary>
        public static void Run()
        {
            Console.WriteLine("--- STARTING SCENARIO: TIGHT COUPLING (BEFORE) ---");
            
            // Step 1: Show the Happy Path / Real World usage
            // This demonstrates how the core logic is shackled to the infrastructure.
            var monitor = new ServerMonitor();
            
            Console.Write("Check 80 degrees: ");
            monitor.CheckTemperature(80); 
            
            Console.Write("Check 96 degrees: ");
            monitor.CheckTemperature(96);

            Console.WriteLine("\n----------------------------------------\n");

            // Step 2: Demonstrate the testing failure
            AttemptedTest.Run();

            Console.WriteLine("\n--- SCENARIO COMPLETE ---");
        }
    }
}