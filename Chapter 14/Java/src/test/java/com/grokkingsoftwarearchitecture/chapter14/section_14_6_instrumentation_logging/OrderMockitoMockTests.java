package com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging;

import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.ports.PaymentPort;
import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.services.OrderService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Listing 14.4 — Pattern B: The Enterprise Mocking Framework (Mockito)
 *
 * Book file: com/ecommerce/order/tests/OrderMockitoMockTests.java
 */
public class OrderMockitoMockTests {

    @Test
    void testOrderCheckoutWithMockitoMock() {
        // 1. Arrange: Construct a highly instrumented dynamic proxy via Mockito
        PaymentPort mockPaymentPort = mock(PaymentPort.class);
        when(mockPaymentPort.process(anyDouble())).thenReturn(true);
        OrderService service = new OrderService(mockPaymentPort);

        // 2. Act: Trigger the system transaction path
        boolean result = service.checkout("ord_99812", 150.00);

        // 3. Assert & Verify behavioral interaction contracts
        assertTrue(result);
        verify(mockPaymentPort, times(1)).process(150.00);
    }
}