/**
 * Instrumented service layer handling transactional checkout workflows.
 * Embeds inside-out semantic telemetry using AsyncLocalStorage
 * (Node.js equivalent of Java's MDC / Thread-Local Storage).
 *
 * Book listing: com.ecommerce.order.services.OrderService — Listing 14.2
 */
import { AsyncLocalStorage } from "node:async_hooks";

// Node.js equivalent of Java's MDC (Mapped Diagnostic Context):
// AsyncLocalStorage provides async-context sandboxing.
export const orderIdContext = new AsyncLocalStorage();

export class OrderService {
  /**
   * @param {import("../ports/paymentPort.js").PaymentPort} paymentPort
   */
  constructor(paymentPort) {
    this.paymentPort = paymentPort;
  }

  /**
   * Execute a checkout with MDC context binding.
   * @param {string} orderId
   * @param {number} amount
   * @returns {boolean}
   */
  checkout(orderId, amount) {
    return orderIdContext.run({ orderId }, () => {
      // Read the active thread-local context once and reuse it for all log statements.
      // This mirrors how MDC values are read by the logging framework in Java.
      const activeOrderId = orderIdContext.getStore()?.orderId;

      console.log(`[INFO] Executing transaction payment processing phase | orderId=${activeOrderId}`);

      const paymentSuccess = this.paymentPort.process(amount);

      if (!paymentSuccess) {
        console.log(`[ERROR] Payment transaction rejected by outbound payment port provider | orderId=${activeOrderId}`);
        return false;
      }

      console.log(`[INFO] Transaction payment processed successfully | orderId=${activeOrderId}`);
      return true;
    });
  }
}