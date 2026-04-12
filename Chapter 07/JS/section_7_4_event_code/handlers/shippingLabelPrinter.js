import { Consumer } from './consumer.js';
import { of, delay, tap } from 'rxjs';

/**
 * THE CONSUMER: A decoupled background worker.
 * This simulates a completely independent system. Because of Temporal Decoupling,
 * it operates entirely on its own timeline, processing work as fast as it can 
 * without forcing the original web request to wait.
 */
export class ShippingLabelPrinter extends Consumer {
    /**
     * Processes the event by constructing an Observable pipeline.
     * @param event - The OrderPlaced event.
     * @returns The reactive stream pipeline.
     */
    handle(event) {
        return of(event).pipe(
            tap(e => {
                console.log(`\n[Shipping Service] Waking up...`);
                console.log(`[Shipping Service] CorrelationId matched: ${e.correlationId}`);
                console.log(`[Shipping Service] Printing Label for Order: ${e.orderId}`);
                console.log(`[Shipping Service] Package Value: $${e.totalAmount.toFixed(2)}`);
            }),
            delay(1000),
            tap(() => {
                console.log(`[Shipping Service] Label Printed! ACK sent to broker.\n`);
            })
        );
    }
}