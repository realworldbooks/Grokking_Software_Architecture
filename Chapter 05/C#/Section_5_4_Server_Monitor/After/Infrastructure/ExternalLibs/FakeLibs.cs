using System;

namespace Chapter05.ServerMonitor.After.Infrastructure.ExternalLibs
{
    /// <summary>
    /// Dummies to allow compilation without installing real NuGet packages.
    /// Represents the "Chaotic Outside World" SDKs.
    /// </summary>
    public class TwilioClient
    {
        private readonly string _key;

        public TwilioClient(string key) { _key = key; }

        public void SendSms(string to, string msg) 
        {
            // Accessing _key prevents the "mark as static" warning.
            _ = _key; 
        }
    }

    /// <summary>
    /// Interface for a generic message producer.
    /// Uses 'in' for contravariance to maximize architectural flexibility.
    /// </summary>
    public interface IProducer<in TKey, in TValue>
    {
        void Produce(TKey key, string topic, TValue value);
    }
}