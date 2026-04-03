// Dummies to allow compilation without installing real NuGet packages
namespace Chapter05.ServerMonitor.After.Infrastructure.ExternalLibs
{
    public class TwilioClient
    {
        public TwilioClient(string key) { }
        public void SendSms(string to, string msg) { }
    }

    public interface IProducer<TKey, TValue>
    {
        void Produce(string topic, TValue value);
    }
    
    public class FakeKafkaProducer : IProducer<string, string>
    {
        public void Produce(string topic, string value) 
        {
             Console.WriteLine($"[Kafka] Pushed to {topic}: {value}");
        }
    }
}