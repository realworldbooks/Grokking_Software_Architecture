package com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.ports;

import java.util.Map;

/**
 * THE CORE PORT (The Asynchronous Airlock):
 * * @description 
 * Defines the contract for deferring work. This represents the "Producer" 
 * side of a message handoff.
 * * ARCHITECTURAL CRITIQUE:
 * 1. ASYNC HANDOFF: This port ensures the Core can offload data without 
 * waiting for a physical payment response, protecting the system from 
 * cascading latency.
 * 2. PORTABILITY: By defining this as a Java interface, we allow the 
 * system to swap between a local MVStore (for the lab) and a production 
 * RabbitMQ broker without touching the Core logic.
 */
public interface MessageQueue {
    /**
     * Deploys a payload to the durable message store.
     * @param payload The transaction data to be secured.
     */
    void enqueue(Map<String, Object> payload);
}