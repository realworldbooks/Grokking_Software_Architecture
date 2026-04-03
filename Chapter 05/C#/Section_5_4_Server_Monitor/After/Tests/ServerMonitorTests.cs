using System;
using Chapter05.ServerMonitor.After.Core.Domain;

// ALIAS FIX: Tells the compiler exactly which ServerMonitor to use
using MonitorClass = Chapter05.ServerMonitor.After.Core.Domain.ServerMonitor;

namespace Chapter05.ServerMonitor.After.Tests
{
    /// <summary>
    /// ARCHITECTURAL TEST
    /// Fulfills the Scribe role by proving the test passes without hitting a real API.
    /// </summary>
    public static class ServerMonitorTests
    {
        public static void Run()
        {
            Console.WriteLine("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
            
            // Arrange
            var fakePort = new FakeAlertPort();
            
            // Using the alias to avoid the namespace collision
            var monitor = new MonitorClass(fakePort);

            // Act
            Console.WriteLine("Test Action: Checking temperature at 96 degrees...");
            monitor.CheckTemperature(96);

            // Assert
            if (fakePort.SentMessages.Count == 1 && fakePort.SentMessages[0].Contains("Take cover"))
            {
                Console.WriteLine("SUCCESS: Alert sent correctly to the Port.");
            }
            else
            {
                Console.WriteLine("FAIL: Alert logic failed verification.");
            }
        }
    }
}