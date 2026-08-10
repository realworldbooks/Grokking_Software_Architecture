/**
 * Demo runner for Section 14.6 — Unit Test and Instrumentation Logging.
 *
 * Demonstrates Listings 14.1–14.5:
 *   14.1 PaymentPort (outbound infrastructure port)
 *   14.2 OrderService (AsyncLocalStorage thread-local context)
 *   14.3 Pattern A: Hand-Coded Stub
 *   14.4 Pattern B: Mock
 *   14.5 Automated Telemetry Quality Gate
 */
import { HappyPathPaymentPort } from "./ports/happyPathPaymentPort.js";
import { OrderService, orderIdContext } from "./services/orderService.js";

export class Demo {
  static async run() {
    console.log("=== Section 14.6: Unit Test and Instrumentation Logging ===\n");

    // --- Listing 14.1: PaymentPort (port defined in ports folder) ---
    console.log("--- Listing 14.1: PaymentPort (Outbound Infrastructure Port) ---");
    console.log("PaymentPort defines the boundary contract:");
    console.log("    process(amount) -> boolean");
    console.log("Core business logic depends on this abstraction, not a concrete HTTP client.\n");

    // --- Listing 14.2: OrderService with AsyncLocalStorage (MDC equivalent) ---
    console.log("--- Listing 14.2: OrderService (AsyncLocalStorage Thread-Local Context) ---");
    const happyPathPort = new HappyPathPaymentPort();
    const service = new OrderService(happyPathPort);
    const success = service.checkout("ord_99812", 150.0);
    console.log("Checkout result: " + success);
    console.log("AsyncLocalStorage context after checkout (should be null): " + (orderIdContext.getStore() ?? "null"));
    console.log();

    // --- Listing 14.3: Pattern A — Hand-Coded Stub ---
    console.log("--- Listing 14.3: Pattern A — Minimal Hand-Coded Stub ---");
    const stubService = new OrderService(new HappyPathPaymentPort());
    const stubResult = stubService.checkout("ord_99812", 150.0);
    console.log("Hand-coded stub checkout result: " + stubResult);
    console.log("(Stub is passive — cannot audit invocation counts.)\n");

    // --- Listing 14.4: Pattern B — Mock (conceptual demo) ---
    console.log("--- Listing 14.4: Pattern B — Enterprise Mocking Framework ---");
    console.log("In the test suite, a dynamic proxy records invocation history:");
    console.log("    mock.process = (amount) => { callCount++; return true; };");
    console.log("    assert.strictEqual(callCount, 1);");
    console.log("The mock records invocation history and enforces interaction contracts.\n");

    // --- Listing 14.5: Automated Telemetry Quality Gate (conceptual demo) ---
    console.log("--- Listing 14.5: Automated Telemetry Quality Gate ---");
    console.log("The test suite intercepts the port boundary to assert AsyncLocalStorage context:");
    console.log("    assert.strictEqual(orderIdContext.getStore()?.orderId, 'ord_99812');");
    console.log("    assert.strictEqual(orderIdContext.getStore() ?? null, null);  // after checkout");
    console.log("This guarantees telemetry compliance on every build.\n");

    console.log("=== Demo Complete ===");
    console.log("Run 'node --test section_14_6_instrumentation_logging/tests/' to execute the test suite.");
  }
}