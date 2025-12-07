using System;
using ServerMonitor.After.Core.Ports;
using ServerMonitor.After.Infrastructure.ExternalLibs; // Fake 3rd party lib

namespace ServerMonitor.After.Infrastructure.Adapters
{
    // ADAPTER 1: The "Real" Production Adapter
    public class TwilioAdapter : IAlertPort
    {
        public void SendAlert(string message)
        {
            var client = new TwilioClient("API_KEY");
            client.SendSms("555-1234", message);
            Console.WriteLine($"(PROD ADAPTER) SMS sent via Twilio: {message}");
        }
    }
}