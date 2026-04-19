using System;
using System.Threading.Tasks;
using Chapter10.Resilient.Core.Domain;
using Chapter10.Resilient.Core.Ports;

namespace Chapter10.Resilient.Core.Application
{
    /// <summary>
    /// THE CORE APPLICATION LAYER:
    /// * @description
    /// This orchestrator coordinates the business flow using Ports. It 
    /// manages the "What" (Business Policy) while remaining blind 
    /// to the "How" (Infrastructure Implementation).
    ///
    /// ARCHITECTURAL CRITIQUE:
    /// 1. PIVOT ON FAILURE: When the primary gateway's internal retries are 
    ///    exhausted, the Orchestrator executes 'Plan B' (The Queue).
    /// 2. IDEMPOTENCY: The key is generated in the Core. This ensures that 
    ///    if a message is processed later from the queue, we don't 
    ///    double-charge the customer.
    /// </summary>
    public class CheckoutOrchestrator
    {
        private readonly IPaymentGateway _paymentPort;
        private readonly IMessageQueue _queuePort;

        public CheckoutOrchestrator(IPaymentGateway paymentPort, IMessageQueue queuePort)
        {
            _paymentPort = paymentPort;
            _queuePort = queuePort;
        }

        public async Task<OrderStatus> ProcessCheckout(string orderId, decimal amount)
        {
            // Idempotency generation is a Core Business concern.
            string idempotencyKey = Guid.NewGuid().ToString();

            try
            {
                // 1. THE HAPPY PATH (Hidden retries happen inside the adapter)
                await _paymentPort.ChargeAsync(amount, orderId, idempotencyKey);
                Console.WriteLine("      [Core Application] PRIMARY SUCCESS: Transaction PAID.");
                return OrderStatus.Paid;
            }
            catch (Exception ex)
            {
                // 2. THE FALLBACK (Plan B)
                Console.WriteLine($"      [Core Application] PRIMARY FAILED: {ex.Message}");
                Console.WriteLine("      [Core Application] EXECUTING PLAN B: Securing data in Queue.");

                // We bundle the data into a unified payload for the Port
                var payload = new
                {
                    OrderId = orderId,
                    Amount = amount,
                    Status = OrderStatus.PendingPayment.ToString(),
                    IdempotencyKey = idempotencyKey,
                    QueuedAt = DateTime.UtcNow
                };

                await _queuePort.Enqueue(payload);

                return OrderStatus.PendingPayment;
            }
        }
    }
}