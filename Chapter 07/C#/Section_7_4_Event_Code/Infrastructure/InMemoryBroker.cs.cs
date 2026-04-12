using System;
using System.Threading.Channels;
using System.Threading.Tasks;
using Chapter07.EventCode.Shared;
using Chapter07.EventCode.Core;
using Chapter07.EventCode.Handlers;

namespace Chapter07.EventCode.Infrastructure
{
    /// <summary>
    /// THE SHOCK ABSORBER: Simulates a real message broker (like RabbitMQ or Azure Service Bus).
    /// This infrastructure layer completely decouples the Producer from the Consumer.
    /// </summary>
    public class InMemoryBroker : IEventPublisher
    {
        // We use C# Channels to act as an in-memory queue. 
        // It safely holds messages in memory until a background consumer asks for them.
        private readonly Channel<IEvent> _channel = Channel.CreateUnbounded<IEvent>();
        private readonly ShippingLabelPrinter _shippingService = new ShippingLabelPrinter();

        /// <summary>
        /// Accepts a message and immediately returns control to the caller.
        /// </summary>
        public async Task PublishAsync<T>(T @event) where T : IEvent
        {
            await _channel.Writer.WriteAsync(@event);
        }

        /// <summary>
        /// Simulates the background worker continuously pulling new messages as they arrive,
        /// completely independent of the web request lifecycle.
        /// </summary>
        public async Task StartListeningAsync()
        {
            await foreach (var @event in _channel.Reader.ReadAllAsync())
            {
                if (@event is OrderPlaced orderPlacedEvent)
                {
                    await _shippingService.HandleAsync(orderPlacedEvent);
                }
            }
        }
    }
}