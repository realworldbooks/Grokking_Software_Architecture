// --- Step 1: The Individual Service Classes ---

// Handles only validation logic
class OrderValidator {
    validate(order) {
        console.log("  [Validate] Validating order...");
        if (order.items.length === 0 || order.total <= 0) {
            throw new Error("Order is invalid.");
        }
    }
}

// Handles only payment processing
class PaymentService {
    processPayment(order) {
        console.log(`  [Payment] Processing payment for $${order.total.toFixed(2)}...`);
        // Real payment gateway logic would go here
        return true;
    }
}

// Handles only inventory updates
class InventoryManager {
    updateInventory(order) {
        console.log("  [Inventory] Updating inventory...");
        // Real database logic to update stock would go here
    }
}

// Handles only sending notifications
class NotificationService {
    sendConfirmationEmail(order) {
        console.log(`  [Notify] Sending confirmation email to ${order.customerEmail}...`);
        // Real email sending logic would go here
    }
}

module.exports = { OrderValidator, PaymentService, InventoryManager, NotificationService };