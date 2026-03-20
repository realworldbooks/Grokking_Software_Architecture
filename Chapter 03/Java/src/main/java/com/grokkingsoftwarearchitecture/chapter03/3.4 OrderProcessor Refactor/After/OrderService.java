package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

public class OrderService {
    private final OrderValidator validator;
    private final PaymentService paymentService;
    private final InventoryManager inventoryManager;
    private final NotificationService notificationService;

    public OrderService(OrderValidator validator, PaymentService payment, 
                        InventoryManager inventory, NotificationService notifier) {
        this.validator = validator;
        this.paymentService = payment;
        this.inventoryManager = inventory;
        this.notificationService = notifier;
    }

    public String processOrder(Order order) {
        validator.validate(order);

        if (paymentService.processPayment(order)) {
            inventoryManager.updateInventory(order);
            notificationService.sendConfirmationEmail(order);
            return "Order processed successfully.";
        } else {
            return "Payment failed.";
        }
    }
}