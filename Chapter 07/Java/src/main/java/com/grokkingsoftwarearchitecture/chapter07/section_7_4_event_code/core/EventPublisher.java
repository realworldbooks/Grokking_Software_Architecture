package com.grokkingsoftwarearchitecture.chapter07.section_7_4_event_code.core;

import com.grokkingsoftwarearchitecture.chapter07.section_7_4_event_code.shared.Event;

/**
 * THE ABSTRACTION: This keeps your domain logic clean. The Order Service knows it 
 * needs to announce an order, but has no idea whether it goes to RabbitMQ or Kafka.
 */
public interface EventPublisher {
    // The constraint (extends Event) prevents garbage from entering the system
    <T extends Event> void publishAsync(T event);
}