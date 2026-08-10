/**
 * Listing 14.3 — Pattern A: The Minimal Hand-Coded Lambda Stub
 *
 * Book file: com/ecommerce/order/tests/OrderLambdaStubTests.java
 */
import { test } from "node:test";
import assert from "node:assert";
import { HappyPathPaymentPort } from "../ports/happyPathPaymentPort.js";
import { OrderService } from "../services/orderService.js";

test("order checkout with inline stub", () => {
  // Create a sterile, static Test Double with zero network overhead
  const inlinePaymentStub = new HappyPathPaymentPort();
  const service = new OrderService(inlinePaymentStub);

  const result = service.checkout("ord_99812", 150.0);
  assert.strictEqual(result, true, "Checkout failed under a cooperative happy-path stub.");
});