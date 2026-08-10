package com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging;

import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.ports.PaymentPort;
import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.services.OrderService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Listing 14.3 — Pattern A: The Minimal Hand-Coded Lambda Stub
 *
 * Book file: com/ecommerce/order/tests/OrderLambdaStubTests.java
 */
public class OrderLambdaStubTests {

    @Test
    void testOrderCheckoutWithInlineStub() {
        // Create a sterile, static Test Double with zero network overhead
        PaymentPort inlinePaymentStub = amount -> true;
        OrderService service = new OrderService(inlinePaymentStub);

        boolean result = service.checkout("ord_99812", 150.00);
        assertTrue(result, "Checkout failed under a cooperative happy-path stub.");
    }
}