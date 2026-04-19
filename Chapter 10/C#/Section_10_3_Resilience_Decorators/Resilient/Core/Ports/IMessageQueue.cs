using System.Threading.Tasks;

namespace Chapter10.Resilient.Core.Ports
{
    /// <summary>
    /// THE CORE PORT (The Asynchronous Airlock):
    /// * @description
    /// This port defines the system's capability to "defer work." The Core 
    /// logic invokes this when the primary synchronous path is unavailable.
    /// 
    /// PRODUCTION REALITY (How this works on a Server):
    /// 1. TEMPORAL DECOUPLING: By calling 'Enqueue', the Core hands off 
    ///    responsibility to a broker. It does not wait for the payment 
    ///    to actually be processed by a worker.
    /// 2. DURABILITY: In production (SQS/RabbitMQ), this Port ensures that 
    ///    once a message is accepted by the broker, it is persisted to 
    ///    disk/cluster storage, protecting it from app crashes.
    /// </summary>
    public interface IMessageQueue
    {
        /// <summary>
        /// Defines the requirement for persisting an order payload.
        /// </summary>
        /// <param name="payload">The transaction data to be secured.</param>
        Task Enqueue(object payload);
    }
}