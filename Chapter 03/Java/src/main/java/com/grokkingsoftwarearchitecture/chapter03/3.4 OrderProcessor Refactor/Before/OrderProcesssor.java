package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.before;

public class OrderProcessor {
    public String process(Order order) {
        System.out.println("  [Validate] Validating order...");
        if (order.items.isEmpty() || order.total <= 0) {
            throw new IllegalStateException("Order is invalid.");
        }

        System.out.println("  [Payment] Processing payment for $" + order.total + "...");
        boolean paymentSuccess = true;

        if (paymentSuccess) {
            System.out.println("  [Inventory] Updating inventory...");
            System.out.println("  [Notify] Sending confirmation email to " + order.customerEmail + "...");
            return "Order processed successfully.";
        } else {
            return "Payment failed.";
        }
    }
}