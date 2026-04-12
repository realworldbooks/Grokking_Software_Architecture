import crypto from 'crypto';

/**
 * THE VIP BADGE: Base class representing architectural constraints for events.
 * By forcing all events to inherit from this base class, we ensure that every
 * message moving through our reactive pipeline has a standard shape.
 */
export class Event {
    /**
     * Creates a standard base event.
     * @param correlationId - The ID used to trace the full request lifecycle.
     */
    constructor(correlationId) {
        this.eventId = crypto.randomUUID();
        this.correlationId = correlationId || crypto.randomUUID();
        this.occurredOn = new Date().toISOString();
    }
}