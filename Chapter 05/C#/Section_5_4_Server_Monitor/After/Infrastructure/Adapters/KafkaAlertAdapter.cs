using System;
using System.Text.Json; // Native .NET JSON
using Chapter05.ServerMonitor.After.Core.Ports;
using Chapter05.ServerMonitor.After.Infrastructure.ExternalLibs; // Fake 3rd party lib

namespace Chapter05.ServerMonitor.After.Infrastructure.Adapters
{
    // ADAPTER 3: The "Scale" Adapter (Async Messaging)
    public class KafkaAlertAdapter : IAlertPort
    {
        private readonly IProducer<string, string> _kafkaProducer;

        public KafkaAlertAdapter(IProducer<string, string> kafkaProducer)
        {
            _kafkaProducer = kafkaProducer;
        }

        public void SendAlert(string message)
        {
            var payload = new { Error = message, Timestamp = DateTime.UtcNow };
            var json = JsonSerializer.Serialize(payload);
            
            // Fire and forget! The Core doesn't need to wait for an ACK.
            _kafkaProducer.Produce("server-alerts-topic", json);
        }
    }
}