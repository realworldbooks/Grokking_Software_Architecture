package com.grokking.resilience.core.ports;

/**
 * THE CORE PORT (The Primary Airlock):
 * * DESIGN NOTE:
 * This Port is the Core's definition of how it expects to interact with the 
 * physical world. It belongs to the Core, not the Infrastructure.
 * * ARCHITECTURAL CRITIQUE:
 * If this interface were in 'infrastructure', the Core would depend on 
 * Infrastructure—violating the Golden Rule. By placing this in 'core/ports', 
 * we force external adapters to point INWARD to satisfy our business needs.
 */
public interface PaymentGateway {
    boolean charge(double amount, String orderId, String idempotencyKey) throws Exception;
}