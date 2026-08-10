/**
 * A concrete, hand-coded stub implementation of PaymentPort.
 * Simulates a successful payment with zero network overhead.
 */
import { PaymentPort } from "./paymentPort.js";

export class HappyPathPaymentPort extends PaymentPort {
  /** @param {number} amount */
  process(amount) {
    return true;
  }
}