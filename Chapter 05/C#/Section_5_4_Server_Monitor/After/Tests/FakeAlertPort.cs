using System.Collections.Generic;
using Chapter05.ServerMonitor.After.Core.Ports;

namespace Chapter05.ServerMonitor.After.Tests
{
    public class FakeAlertPort : IAlertPort
    {
        public List<string> SentMessages { get; } = new List<string>();
        public void SendAlert(string message) => SentMessages.Add(message);
    }
}
