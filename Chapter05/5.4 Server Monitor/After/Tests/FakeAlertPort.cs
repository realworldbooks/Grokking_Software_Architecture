using System.Collections.Generic;
using ServerMonitor.After.Core.Ports;

namespace ServerMonitor.After.Tests
{
    public class FakeAlertPort : IAlertPort
    {
        public List<string> SentMessages { get; } = new List<string>();
        public void SendAlert(string message) => SentMessages.Add(message);
    }
}
