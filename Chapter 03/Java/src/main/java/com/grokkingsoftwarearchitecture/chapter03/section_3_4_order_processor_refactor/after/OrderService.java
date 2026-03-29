package com.grokkingsoftwarearchitecture.chapter03.section_3_4_order_processor_refactor.after;

/**
 * THE SOLUTION: The Coordinator / Facade.
 * * ARCHITECTURE NOTE: Look closely at this class. Notice what it DOESN'T do:
 * It doesn't validate, it doesn't charge credit cards, and it doesn't send emails. 
 * * Instead, it acts as a high-level "Coordinator". It orchestrates the flow of 
 * the transaction by delegating the actual work to the injected services. 
 * * By using Constructor Injection (DIP), we can easily pass in "Mock" versions 
 * of the PaymentService and NotificationService to completely unit test this 
 * entire checkout flow in milliseconds without ever hitting a real database 
 * or charging a real credit card!
 */
public class OrderService {
    // Dependencies are explicitly declared.
    private final OrderValidator validator;
    private final PaymentService paymentService;
    private final InventoryManager inventoryManager;
    private final NotificationService notificationService;

    /**
     * Dependencies are injected from the outside (Constructor Injection).
     */
    public OrderService(OrderValidator validator, PaymentService payment, 
                        InventoryManager inventory, NotificationService notifier) {
        this.validator = validator;
        this.paymentService = payment;
        this.inventoryManager = inventory;
        this.notificationService = notifier;
    }

    /**
     * The high-level transaction script is now clean, readable, and safe.
     */
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