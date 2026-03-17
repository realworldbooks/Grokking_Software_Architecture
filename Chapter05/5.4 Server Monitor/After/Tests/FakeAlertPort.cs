using System.Collections.Generic;
using ServerMonitor.After.Core.Ports;

namespace ServerMonitor.After.Tests
{
    public class FakeAlertPort : IAlertPort
    {
        public List<string> SentMessages { get; } = new List<string>();
        public void SendAlert(string message) => SentMessages.Add(message);
    }
    // Arrange: Use a simple "Fake" adapter (just a list!)
    var fakePort = new FakeAlertPort(); 
    var monitor = new ServerMonitor(fakePort);

    // Act: Poke the core
    monitor.CheckTemperature(95); #A

    // Assert: Verify the OUTCOME, not the implementation
    Assert.Single(fakePort.SentMessages); #B
    Assert.Contains("Take cover!", fakePort.SentMessages[0]);
}
