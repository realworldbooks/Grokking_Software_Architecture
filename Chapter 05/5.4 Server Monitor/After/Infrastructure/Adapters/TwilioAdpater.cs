using System;
using ServerMonitor.After.Core.Ports;
using ServerMonitor.After.Infrastructure.ExternalLibs; // Fake 3rd party lib

namespace ServerMonitor.After.Infrastructure.Adapters
{
    // ADAPTER 1: The "Real" Production Adapter
    public class TwilioAdapter : IAlertPort
    {
        private readonly string _apiKey; 
        private readonly string _targetPhoneNumber;

        // INJECT the configuration details via the constructor 
        public TwilioAdapter(string apiKey, string targetPhoneNumber) { 
            _apiKey = apiKey; 
            _targetPhoneNumber = targetPhoneNumber; 
        }

        public void SendAlert(string message) #A
        { #A
            var client = new TwilioClient(_apiKey); #A
            client.SendSms(_targetPhoneNumber, message); #A
            Console.WriteLine($"(PROD ADAPTER) SMS sent via Twilio: {message}"); #A
        } #A
}
