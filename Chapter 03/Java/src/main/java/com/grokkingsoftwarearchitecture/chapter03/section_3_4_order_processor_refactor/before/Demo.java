package com.grokkingsoftwarearchitecture.chapter03.section_3_4_order_processor_refactor.before;

import java.util.Arrays;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: Order Processor (BEFORE) ===");
        System.out.println("One massive class handles everything...\n");

        Order order = new Order(Arrays.asList("Book", "Pen"), 25.50, "customer@example.com");
        OrderProcessor processor = new OrderProcessor();
        
        String result = processor.process(order);

        System.out.println("\nRESULT: " + result);
        System.out.println("===========================================\n");
    }
}