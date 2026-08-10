package com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging;

import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.ports.PaymentPort;
import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.services.OrderService;
import org.slf4j.MDC;

/**
 * Demo runner for Section 14.6 — Unit Test and Instrumentation Logging.
 *
 * Demonstrates Listings 14.1–14.5:
 *   14.1 PaymentPort (outbound infrastructure port)
 *   14.2 OrderService (MDC thread-local context)
 *   14.3 Pattern A: Lambda Stub
 *   14.4 Pattern B: Mockito Mock
 *   14.5 Automated Telemetry Quality Gate
 */
public class Demo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== Section 14.6: Unit Test and Instrumentation Logging ===\n");

        // --- Listing 14.1: PaymentPort (interface defined in ports package) ---
        System.out.println("--- Listing 14.1: PaymentPort (Outbound Infrastructure Port) ---");
        System.out.println("Interface PaymentPort defines the boundary contract:");
        System.out.println("    boolean process(double amount);");
        System.out.println("Core business logic depends on this abstraction, not a concrete HTTP client.\n");

        // --- Listing 14.2: OrderService with MDC ---
        System.out.println("--- Listing 14.2: OrderService (MDC Thread-Local Context) ---");
        PaymentPort happyPathPort = amount -> true;
        OrderService service = new OrderService(happyPathPort);
        boolean success = service.checkout("ord_99812", 150.00);
        System.out.println("Checkout result: " + success);
        System.out.println("MDC context after checkout (should be null): " + MDC.get("orderId"));
        System.out.println();

        // --- Listing 14.3: Pattern A — Lambda Stub ---
        System.out.println("--- Listing 14.3: Pattern A — Minimal Hand-Coded Lambda Stub ---");
        PaymentPort inlinePaymentStub = amount -> true;
        OrderService stubService = new OrderService(inlinePaymentStub);
        boolean stubResult = stubService.checkout("ord_99812", 150.00);
        System.out.println("Lambda stub checkout result: " + stubResult);
        System.out.println("(Stub is passive — cannot audit invocation counts.)\n");

        // --- Listing 14.4: Pattern B — Mockito Mock (conceptual demo) ---
        System.out.println("--- Listing 14.4: Pattern B — Enterprise Mocking Framework (Mockito) ---");
        System.out.println("In the test suite, Mockito generates a dynamic proxy:");
        System.out.println("    PaymentPort mock = mock(PaymentPort.class);");
        System.out.println("    when(mock.process(anyDouble())).thenReturn(true);");
        System.out.println("    verify(mock, times(1)).process(150.00);");
        System.out.println("Mockito records invocation history and enforces interaction contracts.\n");

        // --- Listing 14.5: Automated Telemetry Quality Gate (conceptual demo) ---
        System.out.println("--- Listing 14.5: Automated Telemetry Quality Gate ---");
        System.out.println("The test suite intercepts the port boundary to assert MDC context:");
        System.out.println("    assertEquals(\"ord_99812\", MDC.get(\"orderId\"));");
        System.out.println("    assertNull(MDC.get(\"orderId\"));  // after checkout");
        System.out.println("This guarantees telemetry compliance on every build.\n");

        System.out.println("=== Demo Complete ===");
        System.out.println("Run 'mvn test' to execute the full JUnit 5 + Mockito test suite.");
    }
}