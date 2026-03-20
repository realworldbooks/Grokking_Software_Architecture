using System;
using ServerMonitor.After.Core.Domain;
using ServerMonitor.After.Core.Ports;
using ServerMonitor.After.Infrastructure.Adapters;
using ServerMonitor.After.Infrastructure.ExternalLibs;
using ServerMonitor.After.Tests;

class Program
{
    static void Main(string[] args)
        {
            Console.WriteLine("--- Starting Server Monitor ---");

            // 1. Read Configuration 
            // In a real app, this comes from appsettings.json or Environment Variables. 
            // We are hardcoding them here just for the simulation.
            string envApiKey = "SECRET_TWILIO_KEY_12345";
            string envPhoneNumber = "555-999-8888";

            // 2. Choose your Adapter (The "Outside")
            // We inject the configuration values into the adapter so it knows how to communicate.
            var twilioAdapter = new TwilioAdapter(envApiKey, envPhoneNumber);

            // 2. Inject it into the Core (The "Inside")
            // We inject the adapter (IAlertPort) into the core business logic.
            var monitor = new ServerMonitor(twilioAdapter);

            // 4. Run the Application
            monitor.CheckTemperature(80);  // Output: [Core] Temp 80 is normal.
            monitor.CheckTemperature(105); // Output: (PROD ADAPTER) SMS sent to 555-999-8888 via Twilio: Temp is 105 degrees! Take cover!
        }
        
        // Example of running the Unit Test logic manually
        ServerMonitorTests.Run();
    }
