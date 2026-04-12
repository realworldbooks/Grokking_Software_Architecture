using System.Threading.Tasks;
using Chapter07.EventCode.Shared;

namespace Chapter07.EventCode.Core
{
    /// <summary>
    /// THE ABSTRACTION: This keeps your domain logic clean. The Order Service knows it 
    /// needs to announce an order, but has no idea whether it goes to RabbitMQ or Kafka.
    /// </summary>
    public interface IEventPublisher
    {
        // The constraint (where T : IEvent) prevents garbage from entering the system
        Task PublishAsync<T>(T @event) where T : IEvent;
    }
}