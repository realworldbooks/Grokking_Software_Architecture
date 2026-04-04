using Chapter05.ServerMonitor.After.Core.Ports;
using Chapter05.ServerMonitor.After.Infrastructure.ExternalLibs;
using System;

namespace Chapter05.ServerMonitor.After.Infrastructure.Adapters
{
    /// <summary>
    /// ADAPTER 3: The "Scale" Adapter (Async Messaging).
    /// Handles the transformation of domain alerts into JSON messages for Kafka.
    /// </summary>
    public class KafkaAlertAdapter : IAlertPort
    {
        private readonly IProducer<string, string> _producer;

        /// <summary>
        /// Injects a Messaging Producer into the adapter.
        /// </summary>
        public KafkaAlertAdapter(IProducer<string, string> producer)
        {
            _producer = producer;
        }

        /// <summary>
        /// Formats and pushes the alert to an asynchronous message broker.
        /// </summary>
        public void SendAlert(string message)
        {
            // The Adapter is responsible for formatting data for the outside world.
            string payload = $"{{\"Error\": \"{message}\", \"Timestamp\": \"{DateTime.UtcNow}\"}}";
            
            // We use a static key to ensure chronological ordering within a single partition.
            _producer.Produce("Server-01", "server-alerts", payload);
        }
    }
}