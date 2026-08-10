package com.grokkingsoftwarearchitecture.chapter14.section_14_6_instrumentation_logging.ports;

/**
 * Outbound port definition for third-party billing interactions.
 * Decouples core processing execution from concrete network clients.
 *
 * Book listing: com.ecommerce.order.ports.PaymentPort — Listing 14.1
 */
public interface PaymentPort {
    boolean process(double amount);
}