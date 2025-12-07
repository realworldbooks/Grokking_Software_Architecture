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
        Console.WriteLine("--- SERVER MONITOR (HEXAGONAL DEMO) ---");

        // 1. Choose your Adapter (The Plug)
        // Try swapping this line to new TwilioAdapter() or new KafkaAlertAdapter(...)!
        IAlertPort adapter = new ConsoleAdapter();

        // 2. Inject it into the Core (The Socket)
        var monitor = new ServerMonitor(adapter);

        // 3. Run the logic
        // The Core logic runs exactly the same, no matter which adapter is plugged in.
        monitor.CheckTemperature(95);
        
        // Example of running the Unit Test logic manually
        ServerMonitorTests.Run();
    }
