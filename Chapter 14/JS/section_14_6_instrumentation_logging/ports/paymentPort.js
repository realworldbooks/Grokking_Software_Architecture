/**
 * Outbound port definition for third-party billing interactions.
 * Decouples core processing execution from concrete network clients.
 *
 * Book listing: com.ecommerce.order.ports.PaymentPort — Listing 14.1
 */

/**
 * PaymentPort defines the boundary contract.
 * In JavaScript, this is expressed as a structural contract (duck typing):
 * any object with a `process(amount)` method satisfies the port.
 */
export class PaymentPort {
  /**
   * Process a payment for the given amount.
   * @param {number} amount - The payment amount.
   * @returns {boolean} True if payment succeeded.
   */
  process(amount) {
    throw new Error("PaymentPort.process() must be implemented by a concrete adapter.");
  }
}