import { from, timer, throwError } from 'rxjs';
import { retry, mergeMap, tap } from 'rxjs/operators';
import { PaymentGateway } from '../../core/ports/PaymentGateway.js';

/**
 * THE INFRASTRUCTURE ADAPTER (The Implementation):
 * * @description
 * This class encapsulates the Physical Resource Policy using RxJS. 
 * It transforms raw network instability into a predictable, shielded stream.
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. POLICY AS DOCUMENTATION: By maintaining these constants, the Adapter 
 * serves as the living documentation for our vendor SLA.
 * * 2. RECOVERY BOUNDARY: The use of 'BACKOFF_FACTOR' and 'MAX_RETRIES' 
 * ensures we don't accidentally DOS our own vendor during a recovery spike.
 * * 3. FAIL-FAST ON EXHAUSTION: We have optimized the retry logic to ensure 
 * that once the MAX_RETRIES is reached, we throw the error immediately 
 * instead of backing off one last time. This prevents "Temporal Leakage" 
 * where the system hangs for no reason before failing over to the queue.
 */
export class FlakyPaymentsPaymentAdapter extends PaymentGateway {
    static CONNECT_TIMEOUT_MS = 2000;
    static READ_TIMEOUT_MS = 8000;
    static MAX_RETRIES = 5;
    static MIN_TIMEOUT_MS = 2000;
    static MAX_TIMEOUT_MS = 10000;
    static BACKOFF_FACTOR = 2;

    constructor(baseUrl = "https://api.flakypayments.com") {
        super();
        this.baseUrl = baseUrl;
    }

    /**
     * @override
     * @returns {import('rxjs').Observable<boolean>}
     */
    charge(amount, orderId, idempotencyKey) {
        return from(this._performRawRequest(amount, orderId, idempotencyKey)).pipe(
            retry({
                count: FlakyPaymentsPaymentAdapter.MAX_RETRIES,
                delay: (error, retryCount) => {
                    // LOG THE FAILURE REGARDLESS OF THE ATTEMPT COUNT
                    console.warn(`      [Retry Shield] Attempt ${retryCount} failed: ${error.message}.`);

                    // IF WE HAVE EXHAUSTED RETRIES: 
                    // Terminate the stream immediately so the Orchestrator can pivot.
                    if (retryCount >= FlakyPaymentsPaymentAdapter.MAX_RETRIES) {
                        console.error(`      [Retry Shield] MAX_RETRIES (${FlakyPaymentsPaymentAdapter.MAX_RETRIES}) reached. Exhausted.`);
                        return throwError(() => error);
                    }

                    const backoff = FlakyPaymentsPaymentAdapter.MIN_TIMEOUT_MS * Math.pow(FlakyPaymentsPaymentAdapter.BACKOFF_FACTOR, retryCount - 1);
                    const delayTime = Math.min(backoff, FlakyPaymentsPaymentAdapter.MAX_TIMEOUT_MS);
                    
                    console.warn(`      [Retry Shield] Backing off ${delayTime}ms...`);
                    
                    return timer(delayTime);
                }
            }),
            tap(() => console.log(`      [FlakyPayments Adapter] SUCCESS: Order ${orderId} charged.`))
        );
    }

    async _performRawRequest(amount, order_id, key) {
        // Simulated failure to test the shield
        throw new Error("FlakyPayments API: Gateway Timeout (504)");
    }
}