// infrastructure/inMemoryBroker.js

import { Subject, filter, mergeMap } from 'rxjs';
import { EventPublisher } from '../core/eventPublisher.js';
import { ShippingLabelPrinter } from '../handlers/shippingLabelPrinter.js';

/**
 * THE SHOCK ABSORBER: Simulates a real message broker using an RxJS Subject.
 */
export class InMemoryBroker extends EventPublisher {
    constructor() {
        super();
        this.subject = new Subject();
        this.shippingService = new ShippingLabelPrinter();

        // We must store the subscription locally so we can sever the tie 
        // later and prevent the Node Event Loop from leaking memory.
        this.listenerSubscription = null;
    }

    /**
     * Translates the generic 'publish' command into a reactive stream push.
     * @param event - The payload to broadcast.
     */
    publish(event) {
        this.subject.next(event);
    }

    /**
     * Wires up the background listeners to the central Subject.
     */
    startListening() {
        // We capture the returned Subscription object.
        this.listenerSubscription = this.subject.pipe(
            filter(event => event.constructor.name === 'OrderPlaced'),
            mergeMap(event => this.shippingService.handle(event))
        ).subscribe();
    }

    /**
     * Safely tears down the reactive pipeline to prevent memory leaks.
     * This is critical in enterprise systems when services scale down or restart.
     */
    stopListening() {
        if (this.listenerSubscription) {
            this.listenerSubscription.unsubscribe();
            console.log(`[Broker] Listeners unsubscribed. Memory safely reclaimed.`);
        }
    }
}