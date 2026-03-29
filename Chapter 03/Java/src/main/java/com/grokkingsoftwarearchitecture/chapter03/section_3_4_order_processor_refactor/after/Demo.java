package com.grokkingsoftwarearchitecture.chapter03.section_3_4_order_processor_refactor.after;

import java.util.Arrays;

public class Demo {
    public static void run() {
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