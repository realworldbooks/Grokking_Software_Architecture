import crypto from 'crypto';
import { InMemoryBroker } from './infrastructure/inMemoryBroker.js';
import { OrderController } from './controllers/orderController.js';
import { timer } from 'rxjs';

/**
 * THE COMPOSITION ROOT: Where the application wires all the decoupled reactive pieces 
 * together and executes the simulation.
 */
export class Demo {
    static run() {
        console.log("=== Section 7.4: Event Definition & Temporal Decoupling ===\n");

        // 1. Wire up the Shared Infrastructure (The Broker)
        const broker = new InMemoryBroker();

        // 2. Spin up the Consumer in the background
        // We initialize the listener so it operates independently on the event loop.
        broker.startListening();

        // 3. Instantiate the API Service (The Producer)
        const orderController = new OrderController(broker);
        
        // 4. Simulate the user clicking "Checkout"
        // We capture the subscription for the controller action.
        // Because we use 'of()' in the controller, it actually completes automatically, 
        // but explicitly unsubscribing is the safest enterprise habit to build.
        const checkoutSub = orderController.checkout(crypto.randomUUID(), 149.99).subscribe();

        
        // We capture the timer subscription as well.
        const timerSub = timer(2000).subscribe(() => { // Wait enough time for the background queue to process the label printing
            console.log("Press any key to return to menu...");

            // CLEANUP PHASE: Prevent Memory Leaks
            broker.stopListening();
            checkoutSub.unsubscribe();
            timerSub.unsubscribe();
            
            console.log("=== Simulation Complete & Cleaned Up ===");
        });     
    }
}
