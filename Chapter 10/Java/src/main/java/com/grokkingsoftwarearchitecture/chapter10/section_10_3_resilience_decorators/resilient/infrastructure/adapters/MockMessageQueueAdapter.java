package com.grokking.resilience.infrastructure.adapters;

import com.grokking.resilience.core.domain.OrderStatus;
import com.grokking.resilience.core.ports.MessageQueue;

/**
 * THE INFRASTRUCTURE ADAPTER (The Implementation):
 * * DESIGN NOTE:
 * This adapter fulfills the MessageQueue contract. In a production 
 * environment, this would encapsulate the logic for a library like 
 * Spring Cloud Stream, JMS, or the AWS Java SDK.
 */
public class MockMessageQueueAdapter implements MessageQueue {

    @Override
    public void enqueue(String orderId, double amount, OrderStatus status, String idempotencyKey) {
        // ARCHITECTURAL NOTE:
        // We simulate the handoff to a persistent broker. In Java, this 
        // transition from Synchronous (Primary) to Asynchronous (Fallback) 
        // is what prevents "Cascading Failures."
        System.out.println("      [Queue Adapter] Physical connection to broker established...");
        System.out.println("      [Queue Adapter] DATA SECURED: Order " + orderId + " queued for later.");
        System.out.println("      [Queue Adapter] Context: Status=" + status + ", Key=" + idempotencyKey.substring(0, 8) + "...");
    }
}