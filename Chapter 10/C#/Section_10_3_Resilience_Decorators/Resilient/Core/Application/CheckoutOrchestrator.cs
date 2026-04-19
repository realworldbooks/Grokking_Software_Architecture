using System;
using System.Threading.Tasks;
using Chapter10.Resilience.Core.Domain;
using Chapter10.Resilience.Core.Ports;

namespace Chapter10.Resilience.Core.Application;

public class CheckoutOrchestrator
{
    private readonly IPaymentGateway _paymentGateway;
    private readonly IMessageQueue _messageQueue;

    public CheckoutOrchestrator(IPaymentGateway paymentGateway, IMessageQueue messageQueue)
    {
        _paymentGateway = paymentGateway;
        _messageQueue = messageQueue;
    }

    public async Task<OrderStatus> ProcessCheckoutAsync(string orderId, decimal amount)
    {
        // #G: Idempotency Key is a Business-Level safety mechanism.
        // It must remain constant across all retry attempts.
        string idempotencyKey = Guid.NewGuid().ToString();

        try
        {
            // 1. THE HAPPY PATH (Hidden retries happen inside the Adapter)
            await _paymentGateway.ChargeAsync(amount, orderId, idempotencyKey);
            Console.WriteLine("      [Core Application] PRIMARY SUCCESS: Transaction PAID.");
            return OrderStatus.Paid;
        }
        catch (Exception ex)
        {
            // #H: THE FALLBACK (Plan B)
            // When the adapter's Resilience Policy (Polly) finally gives up, 
            // the Orchestrator executes the recovery path.
            Console.WriteLine($"      [Core Application] PRIMARY FAILED: {ex.Message}");
            Console.WriteLine("      [Core Application] EXECUTING PLAN B: Securing data in Queue.");

            await _messageQueue.EnqueueAsync(new {
                OrderId = orderId,
                Total = amount,
                Status = OrderStatus.PendingPayment,
                Key = idempotencyKey
            });

            return OrderStatus.PendingPayment;
        }
    }
}