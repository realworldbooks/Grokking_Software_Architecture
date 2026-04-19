using System.Threading.Tasks;

namespace Chapter10.Resilience.Core.Ports;

/// <summary>
/// THE CORE PORT (The Asynchronous Airlock):
/// 
/// DESIGN NOTE:
/// This port defines the system's capability to "defer work." The Core 
/// logic invokes this when the synchronous payment path is unavailable.
/// 
/// ARCHITECTURAL CRITIQUE:
/// By defining this Port in the Core, we ensure the business logic is 
/// decoupled from specific infrastructure. The Core doesn't care if 
/// we use RabbitMQ, Azure Service Bus, or AWS SQS. It only knows that 
/// it has a reliable way to secure the data during a crisis.
/// </summary>
public interface IMessageQueue
{
    Task EnqueueAsync(object payload);
}