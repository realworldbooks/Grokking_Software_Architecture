/**
 * Listing 14.4 — Pattern B: The Enterprise Mocking Framework
 *
 * Book file: com/ecommerce/order/tests/OrderMockitoMockTests.java
 */
import { test } from "node:test";
import assert from "node:assert";
import { PaymentPort } from "../ports/paymentPort.js";
import { OrderService } from "../services/orderService.js";

test("order checkout with mock", () => {
  // 1. Arrange: Construct a highly instrumented dynamic proxy
  const mockPaymentPort = new PaymentPort();
  let callCount = 0;
  let lastAmount = null;
  mockPaymentPort.process = (amount) => {
    callCount++;
    lastAmount = amount;
    return true;
  };
  const service = new OrderService(mockPaymentPort);

  // 2. Act: Trigger the system transaction path
  const result = service.checkout("ord_99812", 150.0);

  // 3. Assert & Verify behavioral interaction contracts
  assert.strictEqual(result, true);
  assert.strictEqual(callCount, 1, "process() should be called exactly once");
  assert.strictEqual(lastAmount, 150.0, "process() should be called with 150.00");
});