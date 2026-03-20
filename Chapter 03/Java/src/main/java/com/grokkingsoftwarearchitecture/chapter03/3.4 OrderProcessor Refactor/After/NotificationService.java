package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

// Handles only sending notifications
public class NotificationService {
    public void sendConfirmationEmail(Order order) {
        System.out.println("  [Notify] Sending confirmation email to " + order.customerEmail + "...");
        // Real email sending logic would go here
    }
}