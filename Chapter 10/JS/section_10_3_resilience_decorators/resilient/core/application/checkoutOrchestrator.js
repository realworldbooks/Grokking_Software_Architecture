// FIX: Changed from 'node:crypto' to 'crypto' for environment compatibility
import crypto from 'crypto';
import { catchError, map, tap } from 'rxjs';
import { OrderStatus } from '../domain/OrderStatus.js';

/**
 * THE CORE APPLICATION LAYER:
 * * @description
 * This orchestrator coordinates the 'Plan B' for the payment flow. 
 * It manages the "What" (Business Policy) while remaining blind 
 * to the "How" (Infrastructure Implementation).
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. REACTIVE FALLBACK: Using the RxJS 'catchError' operator, the 
 * orchestrator pivots instantly to the Message Queue Port when the 
 * primary synchronous gateway fails.
 * * 2. IDEMPOTENCY MANAGEMENT: The key is generated here in the Core. 
 * If the transaction is enqueued and processed later by a worker, 
 * the same key is used to ensure the customer isn't double-charged.
 * * 3. PORT-ADAPTER PURITY: The orchestrator depends only on the 
 * 'PaymentGateway' and 'MessageQueue' ports. It has no idea if the 
 * infrastructure uses FlakyPayments, Stripe, SQLite, or RabbitMQ.
 */
export class CheckoutOrchestrator {
    /**
     * @param {import('../ports/PaymentGateway.js').PaymentGateway} paymentPort 
     * @param {import('../ports/MessageQueue.js').MessageQueue} queuePort 
     */
    constructor(paymentPort, queuePort) {
        this.paymentPort = paymentPort;
        this.queuePort = queuePort;
    }

    /**
     * @param {string} orderId 
     * @param {number} amount 
     */
    processCheckout(orderId, amount) {
        // Idempotency Key generation is a Business Layer responsibility 
        // to ensure safety across retries and fallbacks.
        const idempotencyKey = crypto.randomUUID();

        // 1. PRIMARY GATEWAY (Sync/Observable Path)
        return this.paymentPort.charge(amount, orderId, idempotencyKey).pipe(
            tap(() => console.log("      [Core Application] PRIMARY SUCCESS: Transaction PAID.")),
            map(() => OrderStatus.PAID),
            
            // 2. FALLBACK (Plan B - Durable Persistence)
            catchError((error) => {
                console.error(`      [Core Application] PRIMARY FAILED: ${error.message}`);
                console.log("      [Core Application] EXECUTING PLAN B: Reactive Fallback.");

                // Pivot the stream to the Queue Port
                return this.queuePort.enqueue({
                    orderId,
                    amount,
                    status: OrderStatus.PENDING_PAYMENT,
                    idempotencyKey
                }).pipe(
                    map(() => OrderStatus.PENDING_PAYMENT)
                );
            })
        );
    }
}