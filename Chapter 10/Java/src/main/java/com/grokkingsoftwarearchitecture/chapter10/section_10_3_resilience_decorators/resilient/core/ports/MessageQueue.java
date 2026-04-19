package com.grokking.resilience.core.ports;

import com.grokking.resilience.core.domain.OrderStatus;

/**
 * THE CORE PORT (The Asynchronous Airlock):
 * * DESIGN NOTE:
 * This port defines the system's capability to "defer work." The Core 
 * logic invokes this when the synchronous payment path is unavailable.
 * * ARCHITECTURAL CRITIQUE:
 * By defining this Port in the Core, we ensure the business logic is 
 * decoupled from specific infrastructure. The Core doesn't care if 
 * we use RabbitMQ, ActiveMQ, or AWS SQS. It only knows that it has a 
 * reliable way to secure the data during an infrastructure crisis.
 */
public interface MessageQueue {
    void enqueue(String orderId, double amount, OrderStatus status, String idempotencyKey);
}