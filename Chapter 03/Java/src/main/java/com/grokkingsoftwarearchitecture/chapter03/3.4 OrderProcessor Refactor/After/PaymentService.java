package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

/**
 * SRP SOLUTION: External API Isolation.
 * * ARCHITECTURE NOTE: If Stripe or PayPal changes their API, this is the 
 * ONLY file that needs to be updated. The rest of the checkout process 
 * remains completely untouched and safe.
 */
public class PaymentService {
    public boolean processPayment(Order order) {
        System.out.println("  [Payment] Processing payment for $" + order.total + "...");
        // Real payment gateway logic would go here
        return true;
    }
}