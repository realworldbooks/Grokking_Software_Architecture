import { Event } from './event.js';
import crypto from 'crypto';

/**
 * THE POCO: An immutable fact representing a completed action.
 * In Event-Driven Architecture, events are named in the past tense because 
 * they represent things that have already happened. 
 */
export class OrderPlaced extends Event {
    /**
     * Constructs the specific data payload for when an order is placed.
     * @param {string} correlationId - The thread tying this back to the user's web request.
     * @param {string} userId - The user who placed the order.
     * @param {number} totalAmount - The total cost.
     */
    constructor(correlationId, userId, totalAmount) {
        super(correlationId);
        this.orderId = crypto.randomUUID();
        this.userId = userId;
        this.totalAmount = totalAmount;
        
        // We use Object.freeze in vanilla JS to simulate the strict immutability 
        // found in C# records. An event is a historical fact; it must never be altered 
        // by downstream consumers.
        Object.freeze(this); 
    }
}