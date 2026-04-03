using System;
using Chapter05.ServerMonitor.After.Core.Domain;
using Chapter05.ServerMonitor.After.Core.Ports;
using Chapter05.ServerMonitor.After.Infrastructure.Adapters;
using Chapter05.ServerMonitor.After.Tests;

// ALIAS FIX: Tells the compiler exactly which ServerMonitor to use
using MonitorClass = Chapter05.ServerMonitor.After.Core.Domain.ServerMonitor;

namespace Chapter05.ServerMonitor.After
{
    /// <summary>
    /// The Execution Layer.
    /// This is where the Boundary Keeper defines the environment.
    /// It acts as the 'Chief Explainer' for the Hexagonal architecture.
    /// </summary>
    public static class Demo
    {
        /// <summary>
        /// Entry point for the "After" architectural scenario.
        /// </summary>
        public static void Run()
        {
            Console.WriteLine("--- STARTING SERVER MONITOR (HEXAGONAL) ---");

            // 1. Configuration (Injected from the environment)
            string envApiKey = "SECRET_TWILIO_KEY_12345";
            string envPhoneNumber = "555-999-8888";

            // 2. Adapter Selection (The "Outside")
            var twilioAdapter = new TwilioAdapter(envApiKey, envPhoneNumber);

            // 3. Dependency Injection into the Core (The "Inside")
            // Using the alias to bypass the namespace collision
            var monitor = new MonitorClass(twilioAdapter);

            // 4. Execution
            Console.Write("Check 80 degrees: ");
            monitor.CheckTemperature(80);  // Nominal case

            Console.Write("Check 105 degrees: ");
            monitor.CheckTemperature(105); // Failure case triggers the adapter

            Console.WriteLine("\n----------------------------------------\n");

            // 5. Automated Verification
            ServerMonitorTests.Run();

            Console.WriteLine("\n========================================");
        }
    }
}