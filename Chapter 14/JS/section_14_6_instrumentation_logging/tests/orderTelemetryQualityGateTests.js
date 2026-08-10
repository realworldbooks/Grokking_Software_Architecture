/**
 * Listing 14.5 — Automated Telemetry Quality Gate
 *
 * Book file: com/ecommerce/order/tests/OrderTelemetryQualityGateTests.java (inlined)
 */
import { test } from "node:test";
import assert from "node:assert";
import { PaymentPort } from "../ports/paymentPort.js";
import { OrderService, orderIdContext } from "../services/orderService.js";

test("checkout should maintain MDC context boundary during execution", () => {
  // Arrange: Intercept interface execution to read thread-local variables
  let capturedOrderId = null;

  const customInterceptorPort = new PaymentPort();
  customInterceptorPort.process = (amount) => {
    // Read active thread context values mid-transaction
    capturedOrderId = orderIdContext.getStore()?.orderId ?? null;
    return true;
  };

  const service = new OrderService(customInterceptorPort);

  // Act: Trigger the system transaction path
  service.checkout("ord_99812", 75.0);

  // Assert: Context was present mid-transaction
  assert.strictEqual(
    capturedOrderId,
    "ord_99812",
    "Telemetry Gap Error: MDC Context was dropped before crossing the port boundary!"
  );

  // Assert: Ensure clean context teardown to prevent memory leaks
  assert.strictEqual(
    orderIdContext.getStore() ?? null,
    null,
    "Memory Contamination Error: MDC context leaked past the request boundary lifetime!"
  );
});