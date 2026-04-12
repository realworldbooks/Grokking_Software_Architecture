import crypto from 'crypto';
import { OrderPlaced } from '../shared/orderPlaced.js';
import { of, tap } from 'rxjs';

/**
 * THE PRODUCER: The front-door entry point for the user.
 * Its only job is to translate user intent into an Event, throw it onto the 
 * RxJS stream, and return control to the user immediately.
 */
export class OrderController {
    /**
     * @param publisher - Injected via Dependency Inversion.
     */
    constructor(publisher) {
        this.publisher = publisher; 
    }

    /**
     * Processes the checkout request as a reactive stream.
     * @param userId - The ID of the customer.
     * @param amount - The total checkout amount.
     * @returns The reactive stream pipeline.
     */
    checkout(userId, amount) {
        return of(null).pipe(
            tap(() => {
                const correlationId = crypto.randomUUID();

                console.log(`[Order API] POST /api/order/checkout received. CorrelationId: ${correlationId}`);
                console.log(`[Order API] Saving Order to database...`);

                const orderPlaced = new OrderPlaced(correlationId, userId, amount);

                console.log(`[Order API] Publishing OrderPlaced event to broker...`);
                this.publisher.publish(orderPlaced);
                
                // This is the core benefit of Temporal Decoupling:
                console.log(`[Order API] HTTP 202 Accepted. User sees success instantly!`);
            })
        );
    }
}