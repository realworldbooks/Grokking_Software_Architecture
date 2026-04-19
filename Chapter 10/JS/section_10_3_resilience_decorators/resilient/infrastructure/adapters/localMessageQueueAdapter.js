import storage from 'node-persist';
import { from, switchMap, tap, map, shareReplay } from 'rxjs';
import { MessageQueue } from '../../core/ports/MessageQueue.js';

/**
 * PHYSICAL INFRASTRUCTURE ADAPTER (Zero-Server Local Queue):
 * * @description
 * This adapter uses 'node-persist' to create a disk-backed queue via SQLite/File-System. 
 * It provides durability for the laboratory environment without requiring external 
 * brokers like RabbitMQ or cloud services like AWS SQS.
 * * PRODUCTION TRANSLATION (How this works on a real server):
 * 1. THE CONNECTION: In production, 'storage.init' would be replaced by 
 * opening a socket (TCP/AMQP) to a message server like RabbitMQ.
 * * 2. SERIALIZATION: The 'payload' object is serialized into JSON or 
 * Protobuf before being sent over the wire to the broker.
 * * 3. ACKNOWLEDGMENT: The 'setItem' equivalent in a real broker would involve 
 * waiting for an 'Ack' (Acknowledgment) from the server to confirm 
 * persistence on the remote disk.
 * * 4. TEMPORAL DECOUPLING: Once enqueued, a separate Worker process on a 
 * different node would pull this message. The main application is no 
 * longer "held hostage" by the payment vendor's response time.
 */
export class LocalMessageQueueAdapter extends MessageQueue {
    /**
     * @param {string} storagePath - The directory where queue data is stored.
     */
    constructor(storagePath = './.node-persist/payment_backlog') {
        super();
        this.storagePath = storagePath;
        // Ensure initialization happens once and is shared across calls
        this._init$ = from(storage.init({ dir: this.storagePath })).pipe(
            shareReplay(1)
        );
    }

    /**
     * @override
     * @param {Object} payload 
     * @returns {import('rxjs').Observable<void>}
     */
    enqueue(payload) {
        return this._init$.pipe(
            switchMap(() => from(storage.setItem(payload.orderId, payload))),
            tap(() => {
                console.log(`      [Local Queue] DATA PERSISTED: Order ${payload.orderId} secured on disk.`);
                console.log(`      [Local Queue] Local Path: ${this.storagePath}`);
            }),
            map(() => void 0) // Ensure we return Observable<void>
        );
    }
}