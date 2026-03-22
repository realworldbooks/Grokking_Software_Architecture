package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

/**
 * SRP SOLUTION: Domain Logic Isolation.
 * * ARCHITECTURE NOTE: This class has one job: validation. It doesn't know 
 * about databases or payment gateways. This makes it incredibly easy to 
 * unit test our business rules without needing mock APIs or databases.
 */
public class OrderValidator {
    public void validate(Order order) {
        System.out.println("  [Validate] Validating order...");
        if (order.items.isEmpty() || order.total <= 0) {
            throw new IllegalStateException("Order is invalid.");
        }
    }
}