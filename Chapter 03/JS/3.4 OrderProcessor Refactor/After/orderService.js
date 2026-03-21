/**
 * THE SOLUTION: The Coordinator / Facade.
 * * ARCHITECTURE NOTE: This class no longer contains "how-to" logic; it only 
 * contains "when-to" logic. 
 * * By using Constructor Injection, we've achieved full Dependency Inversion (DIP). 
 * This class doesn't care HOW an order is validated or HOW a payment is 
 * processed; it simply coordinates the flow between its injected dependencies.
 */
class OrderService {
    /**
     * Dependencies are injected from the outside.
     */
    constructor(validator, paymentService, inventoryManager, notificationService) {
        this.validator = validator;
        this.paymentService = paymentService;
        this.inventoryManager = inventoryManager;
        this.notificationService = notificationService;
    }

    /**
     * Orchestrates the high-level business process.
     */
    processOrder(order) {
        // Step 1: Delegate validation
        this.validator.validate(order);

        // Step 2: Orchestrate the successful path
        if (this.paymentService.processPayment(order)) {
            this.inventoryManager.updateInventory(order);
            this.notificationService.sendConfirmationEmail(order);
            return "Order processed successfully.";
        } else {
            return "Payment failed.";
        }
    }
}

module.exports = OrderService;