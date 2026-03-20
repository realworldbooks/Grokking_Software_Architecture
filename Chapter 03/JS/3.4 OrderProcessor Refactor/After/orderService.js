class OrderService {
    constructor(validator, paymentService, inventoryManager, notificationService) {
        this.validator = validator;
        this.paymentService = paymentService;
        this.inventoryManager = inventoryManager;
        this.notificationService = notificationService;
    }

    processOrder(order) {
        this.validator.validate(order);

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