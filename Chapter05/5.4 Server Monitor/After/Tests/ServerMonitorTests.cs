using System;
using ServerMonitor.After.Core.Domain;

namespace ServerMonitor.After.Tests
{
    public class ServerMonitorTests
    {
        public static void Run()
        {
            Console.WriteLine("\n--- RUNNING UNIT TEST ---");

            // Arrange
            var fakePort = new FakeAlertPort();
            var monitor = new ServerMonitor(fakePort);

            // Act
            monitor.CheckTemperature(95);

            // Assert
            if (fakePort.SentMessages.Count == 1 && 
                fakePort.SentMessages[0].Contains("Take cover"))
            {
                Console.WriteLine("PASS: Alert was received!");
            }
            else
            {
                Console.WriteLine("FAIL: Alert logic incorrect.");
            }
        }
    }
}