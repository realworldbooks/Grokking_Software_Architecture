package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

// Handles only payment processing
public class PaymentService {
    public boolean processPayment(Order order) {
        System.out.println("  [Payment] Processing payment for $" + order.total + "...");
        // Real payment gateway logic would go here
        return true;
    }
}