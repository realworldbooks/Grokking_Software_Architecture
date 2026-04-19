package com.grokking.resilience.core.application;

import com.grokking.resilience.core.domain.OrderStatus;
import com.grokking.resilience.core.ports.MessageQueue;
import com.grokking.resilience.core.ports.PaymentGateway;
import java.util.UUID;

/**
 * THE CORE APPLICATION LAYER:
 * * DESIGN NOTE:
 * This is the "Brain" of the hexagon. It coordinates the business flow 
 * using Ports. It doesn't know about Resilience4j; it only knows 
 * the 'Policy' for success and the 'Plan B' for failure.
 */
public class CheckoutOrchestrator {
    private final PaymentGateway paymentPort;
    private final MessageQueue queuePort;

    public CheckoutOrchestrator(PaymentGateway paymentPort, MessageQueue queuePort) {
        this.paymentPort = paymentPort;
        this.queuePort = queuePort;
    }

    public OrderStatus processCheckout(String orderId, double amount) {
        // #G: IDEMPOTENCY KEY (Generated in Core Application)
        // CRITICAL: Key remains constant across all retry attempts in the adapter.
        String idempotencyKey = UUID.randomUUID().toString();

        try {
            // 1. THE HAPPY PATH (Hidden retries happen inside the adapter)
            paymentPort.charge(amount, orderId, idempotencyKey);
            System.out.println("      [Core Application] Transaction successful.");
            return OrderStatus.PAID;
            
        } catch (Exception e) {
            // #H: THE FALLBACK (Plan B)
            System.out.println("      [Core Application] PRIMARY FAILED. Executing Plan B.");
            
            queuePort.enqueue(orderId, amount, OrderStatus.PENDING_PAYMENT, idempotencyKey);
            return OrderStatus.PENDING_PAYMENT;
        }
    }
}