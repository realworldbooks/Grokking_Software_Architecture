package chapter07.eventcode.shared;

import java.time.Instant;
import java.util.UUID;

/**
 * THE POCO: An immutable fact representing a completed action.
 * Notice the past-tense naming ("OrderPlaced") and the use of the 'record' 
 * keyword, which guarantees this object cannot be changed once created.
 */
public record OrderPlaced(
    // The unique ID of this specific message occurrence in the system
    UUID eventId,

    // The "Thread" ID that tracks the user's request across multiple distributed services
    UUID correlationId,

    // Minimal payload details. We do not include the entire Customer Profile here.
    UUID orderId,
    UUID userId,
    double totalAmount,
    
    // The exact timestamp the fact occurred
    Instant occurredOn
) implements Event {
    public OrderPlaced(UUID correlationId, UUID orderId, UUID userId, double totalAmount) {
        this(UUID.randomUUID(), correlationId, Instant.now(), orderId, userId, totalAmount);
    }
}