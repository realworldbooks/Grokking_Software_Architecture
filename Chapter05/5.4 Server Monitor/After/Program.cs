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
        // Try swapping this line to new ConsoleAdapter() or new KafkaAlertAdapter(...)!
        IAlertPort myAdapter = new TwilioAdapter();

        // 2. Inject it into the Core (The Socket)
        ServerMonitor monitor = new ServerMonitor(myAdapter);

        // 3. Run the logic
        // The Core logic runs exactly the same, no matter which adapter is plugged in.
        monitor.CheckTemperature(105);
        
        // Example of running the Unit Test logic manually
        ServerMonitorTests.Run();
    }
