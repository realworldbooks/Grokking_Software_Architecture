/**
 * THE CORE PORT (The Reactive Airlock):
 * * @description
 * This port defines the system's capability to "defer work" using RxJS Observables. 
 * It represents an asynchronous boundary where the Core can offload data to 
 * protect the user experience from infrastructure instability.
 * * ARCHITECTURAL CRITIQUE:
 * 1. DECOUPLING: By returning an Observable, the Core remains unaware of 
 * the physical implementation (SQLite, RabbitMQ, or SQS). It only cares 
 * about the stream's completion or error.
 * * 2. BACKPRESSURE: Unlike a Promise, an Observable stream allows the system 
 * to handle backpressure and stream cancellation natively if the 
 * underlying adapter supports it.
 * * 3. PRODUCTION REALITY: In a real server environment, this 'enqueue' 
 * represents a handoff to a persistent broker. Once the stream emits 
 * a value, the Core is guaranteed the data is safely outside the 
 * application process.
 */
export class MessageQueue {
    /**
     * @param {Object} payload - The transaction data to be secured.
     * @returns {import('rxjs').Observable<void>}
     */
    enqueue(payload) {
        throw new Error("Port Method 'enqueue' must be implemented by an Adapter.");
    }
}