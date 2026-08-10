package com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging;

import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.ports.PaymentPort;
import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.services.OrderService;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Listing 14.5 — Automated Telemetry Quality Gate
 *
 * Book file: com/ecommerce/order/tests/OrderTelemetryQualityGateTests.java (inlined)
 */
public class OrderTelemetryQualityGateTests {

    @Test
    void testCheckout_Should_MaintainMdcContextBoundaryDuringExecution() {
        // Arrange: Intercept interface execution to read Thread-Local variables
        PaymentPort customInterceptorPort = amount -> {
            // Read active thread context values mid-transaction
            String activeOrderId = MDC.get("orderId");
            assertEquals("ord_99812", activeOrderId,
                "Telemetry Gap Error: MDC Context was dropped before crossing the port boundary!");
            return true;
        };

        OrderService service = new OrderService(customInterceptorPort);

        // Act: Trigger the system transaction path
        service.checkout("ord_99812", 75.00);

        // Assert: Ensure clean thread teardown to prevent memory context leaks
        assertNull(MDC.get("orderId"),
            "Memory Contamination Error: MDC context leaked past the request boundary lifetime!");
    }
}