using System;
using System.Threading.Tasks;
using Chapter07.EventCode.Shared;

namespace Chapter07.EventCode.Handlers
{
    /// <summary>
    /// THE CONSUMER: A background worker simulating an independent microservice.
    /// It operates entirely on its own timeline, processing work as fast as it can.
    /// This replaces the Controller Action as the entry point for your logic.
    /// </summary>
    public class ShippingLabelPrinter : IConsumer<OrderPlaced>
    {
        public async Task HandleAsync(OrderPlaced @event)
        {
            Console.WriteLine($"\n[Shipping Service] Waking up...");
            
            // We extract the Correlation ID to link this background work 
            // back to the original Web API request in our logs.
            Console.WriteLine($"[Shipping Service] CorrelationId matched: {@event.CorrelationId}");
            Console.WriteLine($"[Shipping Service] Printing Label for Order: {@event.OrderId}");
            Console.WriteLine($"[Shipping Service] Package Value: {@event.TotalAmount:C}");
            
            // Simulate the time it takes to generate and print a label
            await Task.Delay(1000); 
            
            // The Handshake: Telling the broker the job is successfully done
            Console.WriteLine($"[Shipping Service] Label Printed! ACK sent to broker.\n");
        }
    }
}