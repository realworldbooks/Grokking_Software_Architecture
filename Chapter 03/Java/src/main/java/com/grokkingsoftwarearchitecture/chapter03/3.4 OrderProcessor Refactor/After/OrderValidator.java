package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

// --- Step 1: The Individual Service Classes ---

// Handles only validation logic
public class OrderValidator {
    public void validate(Order order) {
        System.out.println("  [Validate] Validating order...");
        if (order.items.isEmpty() || order.total <= 0) {
            throw new IllegalStateException("Order is invalid.");
        }
    }
}