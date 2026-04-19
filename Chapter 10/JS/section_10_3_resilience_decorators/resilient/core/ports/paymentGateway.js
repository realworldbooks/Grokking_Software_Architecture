/**
 * THE CORE PORT (The Primary Airlock):
 * * @interface
 * @description
 * Since Vanilla JS doesn't have native Interfaces, we provide a 
 * "Contract Template." Any adapter must implement a 'charge' method.
 * * ARCHITECTURAL CRITIQUE:
 * By defining this Port in the Core, we invert the dependency. 
 * Infrastructure now depends on the Core's requirements. This is 
 * the heart of a "Clarity Engineer's" boundary management.
 */
export class PaymentGateway {
    /**
     * @param {number} amount 
     * @param {string} orderId 
     * @param {string} idempotencyKey 
     * @returns {Promise<boolean>}
     */
    async charge(amount, orderId, idempotencyKey) {
        throw new Error("Port Method 'charge' must be implemented by an Adapter.");
    }
}