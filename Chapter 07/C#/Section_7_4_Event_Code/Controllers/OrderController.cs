using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc; // <-- Required for API attributes
using Chapter07.EventCode.Shared;
using Chapter07.EventCode.Core;

namespace Chapter07.EventCode.Controllers
{
    /// <summary>
    /// THE PRODUCER (The Controller): The front-door entry point for the user. 
    /// Its only job is to translate user intent into an Event and hand it off.
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class OrderController : ControllerBase // <-- Inherit from ControllerBase
    {
        private readonly IEventPublisher _publisher;

        public OrderController(IEventPublisher publisher)
        {
            _publisher = publisher; // Dependency Inversion!
        }

        /// <summary>
        /// Demonstrates Temporal Decoupling: The Controller does not wait for the shipping label.
        /// </summary>
        [HttpPost("checkout")]
        public async Task<IActionResult> CheckoutAsync(Guid userId, decimal amount)
        {
            // The Correlation ID is generated at the very start of the request
            var correlationId = Guid.NewGuid();
            var orderId = Guid.NewGuid();

            Console.WriteLine($"[Order API] POST /api/order/checkout received. CorrelationId: {correlationId}");
            Console.WriteLine($"[Order API] Saving Order {orderId} to database...");

            // 1. Create the Event (The Fact)
            var orderPlaced = new OrderPlaced
            {
                CorrelationId = correlationId,
                OrderId = orderId,
                UserId = userId,
                TotalAmount = amount
            };

            // 2. Publish it (The Handoff)
            // Once this awaits, the Controller washes its hands of the responsibility.
            Console.WriteLine($"[Order API] Publishing OrderPlaced event to broker...");
            await _publisher.PublishAsync(orderPlaced);

            // 3. Respond instantly (Client-Side Intelligence / Fast 202 Accepted)
            Console.WriteLine($"[Order API] HTTP 202 Accepted. User sees success instantly!");
            
            // Return native ASP.NET Core 202 Accepted response
            return Accepted(); 
        }
    }
}