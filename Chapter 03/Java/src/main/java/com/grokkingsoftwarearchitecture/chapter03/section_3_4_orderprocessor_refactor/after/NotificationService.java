package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

/**
 * SRP SOLUTION: Communications Isolation.
 * * ARCHITECTURE NOTE: Email formatting and delivery logic lives purely here.
 * If an email template fails to render, it will no longer crash the entire 
 * payment transaction!
 */
public class NotificationService {
    public void sendConfirmationEmail(Order order) {
        System.out.println("  [Notify] Sending confirmation email to " + order.customerEmail + "...");
        // Real email sending logic would go here
    }
}