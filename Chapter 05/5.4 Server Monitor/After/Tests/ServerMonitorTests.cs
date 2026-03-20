using Microsoft.VisualStudio.TestTools.UnitTesting;
using ServerMonitor.After.Core.Domain;

namespace ServerMonitor.After.Tests
{
    [TestClass]
    public class ServerMonitorTests
    {
        [TestMethod]
        public void ServerOverheating_SendsAlert_ExactlyOnce()
        {
            // Arrange
            var fakePort = new FakeAlertPort();
            var monitor = new ServerMonitor(fakePort);

            // Act
            monitor.CheckTemperature(95);

            // Assert
            Assert.AreEqual(1, fakePort.SentMessages.Count);
            Assert.IsTrue(fakePort.SentMessages[0].Contains("Take cover"));
        }
    }
}
