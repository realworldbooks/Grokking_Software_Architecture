package com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.services;

import com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.ports.PaymentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Instrumented service layer handling transactional checkout workflows.
 * Embeds inside-out semantic telemetry using Thread-Local context.
 *
 * Book listing: com.ecommerce.order.services.OrderService — Listing 14.2
 */
public class OrderService {
    private final PaymentPort paymentPort;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    // Loose coupling achieved via constructor dependency injection (IoC)
    public OrderService(PaymentPort paymentPort) {
        this.paymentPort = paymentPort;
    }

    public boolean checkout(String orderId, double amount) {
        // Programmatically bind unique transaction metadata to the thread context
        try (var context = MDC.putCloseable("orderId", orderId)) {
            logger.info("Executing transaction payment processing phase");

            boolean paymentSuccess = paymentPort.process(amount);

            if (!paymentSuccess) {
                logger.error("Payment transaction rejected by outbound payment port provider");
                return false;
            }

            logger.info("Transaction payment processed successfully");
            return true;
        }
    }
}