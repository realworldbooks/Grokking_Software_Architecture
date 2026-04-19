using LiteDB;
using System;
using System.Threading.Tasks;
using Chapter10.Resilient.Core.Ports;
using Chapter10.Resilient.Core.Domain;

namespace Chapter10.Resilient.Infrastructure.Adapters
{
    /// <summary>
    /// PHYSICAL INFRASTRUCTURE ADAPTER (Zero-Server Local Queue):
    /// 
    /// @description 
    /// This adapter uses LiteDB to create a disk-backed queue. It provides 
    /// durability for the lab environment without requiring external accounts, 
    /// Docker containers, or cloud-native brokers.
    /// 
    /// PRODUCTION ARCHITECTURE (How this works on a real server):
    /// In a live environment (e.g., RabbitMQ, Kafka, or Amazon SQS), 
    /// this 'Enqueue' method functions as follows:
    /// 
    /// 1. THE CONNECTION: Instead of a local file path, it establishes a 
    ///    TCP/IP connection to a broker cluster.
    /// 2. SERIALIZATION: The 'payload' object is serialized into JSON 
    ///    or a binary format (Protobuf) to be sent over the wire.
    /// 3. ACKNOWLEDGMENT: The server sends back an 'Ack' once the message 
    ///    is persisted to its own distributed storage.
    /// 4. ISOLATION: The Core is released instantly, while a separate 
    ///    Worker process pulls the message later for processing.
    /// </summary>
    public class LocalQueueAdapter : IMessageQueue
    {
        private readonly string _connectionString;

        public LocalQueueAdapter(string dbPath = "payment_backlog.db")
        {
            _connectionString = $"Filename={dbPath};Connection=shared";
        }

        public async Task Enqueue(object payload)
        {
            // Simulate the slight latency of a physical I/O handoff
            await Task.Run(() =>
            {
                using (var db = new LiteDatabase(_connectionString))
                {
                    var col = db.GetCollection<object>("outbound_queue");
                    col.Insert(payload);
                }
            });

            Console.WriteLine("      [Local Queue] DATA PERSISTED: Message secured in LiteDB file.");
        }
    }
}