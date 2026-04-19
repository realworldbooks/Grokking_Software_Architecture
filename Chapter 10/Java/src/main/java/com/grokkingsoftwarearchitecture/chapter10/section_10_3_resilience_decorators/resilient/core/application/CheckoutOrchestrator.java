package com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.application;

import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.domain.OrderStatus;
import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.ports.PaymentGateway;
import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.ports.MessageQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.Instant;

/**
 * THE CORE APPLICATION LAYER:
 * * @description
 * This orchestrator coordinates the business flow using Ports. It 
 * manages the "What" (Business Policy) while remaining blind 
 * to the "How" (Infrastructure Implementation).
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. PIVOT ON FAILURE: When the primary gateway's internal retries are 
 * exhausted, the Orchestrator executes 'Plan B' (The Queue).
 * 2. IDEMPOTENCY: The key is generated in the Core. This ensures that 
 * if a message is processed later from the queue, we don't 
 * double-charge the customer.
 */
public class CheckoutOrchestrator {
    private final PaymentGateway paymentPort;
    private final MessageQueue queuePort;

    public CheckoutOrchestrator(PaymentGateway paymentPort, MessageQueue queuePort) {
        this.paymentPort = paymentPort;
        this.queuePort = queuePort;
    }

    /**
     * Executes the checkout process with a reactive fallback policy.
     * * @param orderId The unique identifier for the order.
     * @param amount The total amount to be charged.
     * @return The final status of the order (PAID or PENDING_PAYMENT).
     */
    public OrderStatus processCheckout(String orderId, double amount) {
        // Idempotency generation is a Core Business concern to ensure 
        // safety across retries and fallbacks.
        String idempotencyKey = UUID.randomUUID().toString();

        try {
            // 1. THE HAPPY PATH (Synchronous Gateway)
            paymentPort.charge(amount, orderId, idempotencyKey);
            System.out.println("      [Core Application] PRIMARY SUCCESS: Transaction PAID.");
            return OrderStatus.PAID;

        } catch (Exception e) {
            // 2. THE FALLBACK (Plan B - Durable Persistence)
            System.err.println("      [Core Application] PRIMARY FAILED: " + e.getMessage());
            System.out.println("      [Core Application] EXECUTING PLAN B: Securing data in Queue.");

            // Standardizing the payload for the MessageQueue Port
            Map<String, Object> payload = new HashMap<>();
            payload.put("orderId", orderId);
            payload.put("amount", amount);
            payload.put("status", OrderStatus.PENDING_PAYMENT);
            payload.put("idempotencyKey", idempotencyKey);
            payload.put("queuedAt", Instant.now().toString());

            queuePort.enqueue(payload);

            return OrderStatus.PENDING_PAYMENT;
        }
    }
}