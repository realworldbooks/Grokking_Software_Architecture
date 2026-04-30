/**
 * SRP SOLUTION: Service Isolation.
 * * ARCHITECTURE NOTE: We have broken the monolith into highly focused, 
 * independent service classes. Each class now has exactly ONE reason to 
 * change.
 */

/**
 * Handles only validation logic.
 * ARCHITECTURE NOTE: This can now be unit tested without any external mocks.
 */
class OrderValidator {
    validate(order) {
        console.log("  [Validate] Validating order...");
        if (order.items.length === 0 || order.total <= 0) {
            throw new Error("Order is invalid.");
        }
    }
}

/**
 * Handles only payment processing.
 * ARCHITECTURE NOTE: Isolates third-party API logic (like Stripe or PayPal).
 */
class PaymentService {
    processPayment(order) {
        console.log(`  [Payment] Processing payment for $${order.total.toFixed(2)}...`);
        return true;
    }
}

/**
 * Handles only inventory updates.
 * ARCHITECTURE NOTE: Isolates database/infrastructure concerns.
 */
class InventoryManager {
    updateInventory(order) {
        console.log("  [Inventory] Updating inventory...");
    }
}

/**
 * Handles only sending notifications.
 * ARCHITECTURE NOTE: Isolates communication logic (like SendGrid or AWS SES).
 */
class NotificationService {
    sendConfirmationEmail(order) {
        console.log(`  [Notify] Sending confirmation email to ${order.customerEmail}...`);
    }
}

module.exports = { OrderValidator, PaymentService, InventoryManager, NotificationService };