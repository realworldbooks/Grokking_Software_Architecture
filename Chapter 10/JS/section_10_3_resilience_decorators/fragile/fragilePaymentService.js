/**
 * THE FRAGILE IMPLEMENTATION:
 * * @description
 * This class represents the "Naive" way of handling external dependencies in Node.js. 
 * It treats a remote network call as if it were a reliable, local constant.
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. EVENT LOOP BLOCKING: While 'fetch' is asynchronous, the lack of a timeout 
 * means this Promise could remain pending indefinitely. In Node.js, enough 
 * "hanging" requests will eventually exhaust memory and degrade the 
 * responsiveness of the entire event loop.
 * * 2. ABSTRACTION LEAK: There is no Port (Interface). The business logic is forced 
 * to depend directly on this concrete implementation, violating the 
 * Downward Dependency Rule (Chapter 4).
 * * 3. THE HAPPY PATH FALLACY: The code assumes the network is a "Perfect Pipe." 
 * It provides no mechanism to survive a 503 (Service Unavailable) or a 
 * temporary network "blink." Failure here is absolute and results in a 
 * crashed transaction.
 */
export class FragilePaymentService {
    /**
     * A raw function sitting right in the middle of your business logic.
     * It assumes the outside world is stable and friendly.
     * * @param {number} amount - The dollar amount to charge.
     * @returns {Promise<Object>} The raw JSON response from the vendor.
     */
    async chargeCreditCard(amount) {
        console.log(`      [Payment Service] Attempting to charge $${amount}...`);

        // Raw network call with zero failure policy. 
        // This is an "Unprotected Port."
        const response = await fetch("https://api.zebra.com/charge", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                amount: amount,
                order_id: "12345"
            })
            // NOTE: No Signal/AbortController used. We wait forever.
        });

        // Failure is absolute. No second chances.
        if (!response.ok) {
            throw new Error(`Critical failure from Zebra API: ${response.status}`);
        }

        return await response.json();
    }
}