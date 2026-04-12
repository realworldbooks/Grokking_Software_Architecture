package chapter07.eventcode.shared;

import java.time.Instant;
import java.util.UUID;

/**
 * THE VIP BADGE: An empty marker interface used for architectural constraints.
 * We use this to enforce that our message broker only accepts valid events, 
 * preventing developers from accidentally publishing random strings or database models.
 */
public interface Event {
    UUID eventId();
    UUID correlationId();
    Instant occurredOn();
}