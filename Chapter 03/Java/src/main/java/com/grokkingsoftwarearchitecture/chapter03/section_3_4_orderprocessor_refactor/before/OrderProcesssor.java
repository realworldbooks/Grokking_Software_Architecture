package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.before;

/**
 * ANTI-PATTERN: The "God Class" / Monolithic Transaction Script.
 * * ARCHITECTURE PROBLEM: This class is a massive violation of the Single 
 * Responsibility Principle (SRP) and is tightly coupled to four distinct domains.
 * * If this were a real enterprise application, this one method would:
 * 1. Contain complex business validation rules.
 * 2. Talk to a third-party Payment Gateway API (like Stripe).
 * 3. Connect to a SQL Database to update inventory tables.
 * 4. Talk to a third-party Email API (like SendGrid).
 * * WHY THIS FAILS:
 * - Testing: You cannot test the validation logic without accidentally triggering 
 * real payment calls or database updates.
 * - Maintenance: Four different teams (Billing, Inventory, Communications, Sales) 
 * will constantly be modifying this exact same file, causing merge conflicts.
 * - Brittleness: A timeout error from the Email API could cause the entire 
 * payment process to roll back or crash!
 */
public class OrderProcessor {
    public String process(Order order) {
        
        // 🚨 ARCHITECTURE WARNING: Domain Rule Violation
        // 1. Validation
        System.out.println("  [Validate] Validating order...");
        if (order.items.isEmpty() || order.total <= 0) {
            throw new IllegalStateException("Order is invalid.");
        }

        // 🚨 ARCHITECTURE WARNING: External Integration Coupling
        // 2. Payment Processing
        System.out.println("  [Payment] Processing payment for $" + order.total + "...");
        boolean paymentSuccess = true;

        // 🚨 ARCHITECTURE WARNING: Infrastructure & Communications Coupling
        // 3. Inventory Update & 4. Confirmation Email
        if (paymentSuccess) {
            System.out.println("  [Inventory] Updating inventory...");
            System.out.println("  [Notify] Sending confirmation email to " + order.customerEmail + "...");
            return "Order processed successfully.";
        } else {
            return "Payment failed.";
        }
    }
}