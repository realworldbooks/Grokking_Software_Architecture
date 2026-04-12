package chapter07.eventcode.controllers;

import chapter07.eventcode.core.EventPublisher;
import chapter07.eventcode.shared.OrderPlaced;
import java.util.UUID;

/**
 * THE PRODUCER (The Controller): The front-door entry point for the user. 
 * Its only job is to translate user intent into an Event and hand it off.
 */
public class OrderController {
    private final EventPublisher _publisher;

    public OrderController(EventPublisher publisher) {
        _publisher = publisher; // Dependency Inversion!
    }

    /**
     * Demonstrates Temporal Decoupling: The Controller does not wait for the shipping label.
     */
    public void checkoutAsync(UUID userId, double amount) {
        // The Correlation ID is generated at the very start of the request
        UUID correlationId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        System.out.println("[Order API] POST /api/order/checkout received. CorrelationId: " + correlationId);
        System.out.println("[Order API] Saving Order " + orderId + " to database...");

        // 1. Create the Event (The Fact)
        OrderPlaced orderPlaced = new OrderPlaced(correlationId, orderId, userId, amount);

        // 2. Publish it (The Handoff)
        // Once this returns, the Controller washes its hands of the responsibility.
        System.out.println("[Order API] Publishing OrderPlaced event to broker...");
        _publisher.publishAsync(orderPlaced);

        // 3. Respond instantly (Client-Side Intelligence / Fast 202 Accepted)
        System.out.println("[Order API] HTTP 202 Accepted. User sees success instantly!");
    }
}