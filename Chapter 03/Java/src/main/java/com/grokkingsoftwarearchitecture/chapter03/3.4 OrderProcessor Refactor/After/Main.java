package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.after;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: Order Processor (AFTER) ===");
        System.out.println("A coordinator class delegates to focused services...\n");

        Order order = new Order(Arrays.asList("Book", "Pen"), 25.50, "customer@example.com");
        
        OrderService service = new OrderService(
            new OrderValidator(),
            new PaymentService(),
            new InventoryManager(),
            new NotificationService()
        );

        String result = service.processOrder(order);

        System.out.println("\nRESULT: " + result);
        System.out.println("==========================================\n");
    }
}