/**
 * ANTI-PATTERN: The "God Class" / Monolithic Transaction Script.
 * * ARCHITECTURE PROBLEM: This class is a "Swiss Army Knife" that knows too 
 * much. It violates the Single Responsibility Principle (SRP) by managing 
 * four distinct domains: Validation, Payments, Inventory, and Notifications.
 * * WHY THIS IS DANGEROUS:
 * - Fragility: If the Email API is down, the whole order processing fails.
 * - Testing: You cannot test the validation logic without triggering 
 * console logs (or real side effects) for payments and inventory.
 * - Rigidity: Changing your payment provider (e.g., from Stripe to PayPal) 
 * requires you to open and modify this core business logic file.
 */
class OrderProcessor {
    /**
     * Processes an order by executing four different responsibilities in a row.
     * @param {Order} order - The order to be processed.
     */
    process(order) {
        
        // 🚨 ARCHITECTURE WARNING: Domain Rule Violation
        // Responsibility 1: Validation. 
        // This should be in a separate validator.
        console.log("  [Validate] Validating order...");
        if (order.items.length === 0 || order.total <= 0) {
            throw new Error("Order is invalid.");
        }

        // 🚨 ARCHITECTURE WARNING: External Integration Coupling
        // Responsibility 2: Payment Processing. 
        // This couples the business flow to a specific billing implementation.
        console.log(`  [Payment] Processing payment for $${order.total.toFixed(2)}...`);
        const paymentSuccess = true;

        if (paymentSuccess) {
            // 🚨 ARCHITECTURE WARNING: Infrastructure Coupling
            // Responsibility 3: Inventory Management.
            console.log("  [Inventory] Updating inventory...");

            // 🚨 ARCHITECTURE WARNING: Communications Coupling
            // Responsibility 4: Notifications.
            console.log(`  [Notify] Sending confirmation email to ${order.customerEmail}...`);
            
            return "Order processed successfully.";
        } else {
            return "Payment failed.";
        }
    }
}

module.exports = OrderProcessor;